/**
 * ==============================================================================
 * MICROSERVICO C++ DE PROCESSAMENTO PESADO DE IMAGENS (HTTP REST / SOCKET NATIVO)
 * Disciplina: Compiladores / Infraestrutura em Nuvem (IaaS)
 * Autor: Matheus Sousa dos Santos (Taiwansz)
 * Requisitos:
 *   - Roda como servico de rede escutando na porta configurada via variavel de ambiente ($PORT).
 *   - Le variaveis de ambiente: PORT, NUM_THREADS, BENCHMARK_RES.
 *   - Implementa algoritmo pesado de convolucao 2D (Sobel Edge Detection) com OpenMP.
 *   - Usa sockets Linux nativos (sys/socket.h) sem dependencias externas.
 * ==============================================================================
 */

#include <iostream>
#include <vector>
#include <cmath>
#include <chrono>
#include <fstream>
#include <string>
#include <sstream>
#include <iomanip>
#include <cstdlib>
#include <cstring>

#ifdef _WIN32
#include <winsock2.h>
#include <ws2tcpip.h>
#pragma comment(lib, "ws2_32.lib")
#else
#include <sys/socket.h>
#include <netinet/in.h>
#include <unistd.h>
#include <arpa/inet.h>
#endif

#ifdef _OPENMP
#include <omp.h>
#endif

// Estrutura de Pixel RGB
struct Pixel {
    uint8_t r, g, b;
};

// Formato de Imagem em Memoria
class Image {
public:
    int width;
    int height;
    std::vector<Pixel> data;

    Image(int w, int h) : width(w), height(h), data(w * h) {}

    inline Pixel& at(int x, int y) {
        return data[y * width + x];
    }

    inline const Pixel& at(int x, int y) const {
        return data[y * width + x];
    }

    static Image generateBenchmarkImage(int w, int h) {
        Image img(w, h);
        #pragma omp parallel for collapse(2) schedule(static)
        for (int y = 0; y < h; ++y) {
            for (int x = 0; x < w; ++x) {
                uint8_t pattern = static_cast<uint8_t>((x ^ y) & 0xFF);
                uint8_t circle = static_cast<uint8_t>(std::sin(x * 0.02) * std::cos(y * 0.02) * 127 + 128);
                img.at(x, y) = {pattern, circle, static_cast<uint8_t>((pattern + circle) / 2)};
            }
        }
        return img;
    }

    bool savePPM(const std::string& filename) const {
        std::ofstream file(filename, std::ios::binary);
        if (!file.is_open()) return false;
        file << "P6\n" << width << " " << height << "\n255\n";
        file.write(reinterpret_cast<const char*>(data.data()), data.size() * sizeof(Pixel));
        return true;
    }
};

// Algoritmo Pesado de Convolucao 2D de Sobel
double runHeavyProcessing(int width, int height, const std::string& outputFile) {
    Image src = Image::generateBenchmarkImage(width, height);
    int w = src.width;
    int h = src.height;
    Image dst(w, h);

    const int Gx[3][3] = {
        {-1,  0,  1},
        {-2,  0,  2},
        {-1,  0,  1}
    };

    const int Gy[3][3] = {
        {-1, -2, -1},
        { 0,  0,  0},
        { 1,  2,  1}
    };

    auto start = std::chrono::high_resolution_clock::now();

    #pragma omp parallel for schedule(dynamic, 64)
    for (int y = 1; y < h - 1; ++y) {
        for (int x = 1; x < w - 1; ++x) {
            float sumRx = 0, sumGx = 0, sumBx = 0;
            float sumRy = 0, sumGy = 0, sumBy = 0;

            for (int ky = -1; ky <= 1; ++ky) {
                for (int kx = -1; kx <= 1; ++kx) {
                    const Pixel& p = src.at(x + kx, y + ky);
                    int k_x = Gx[ky + 1][kx + 1];
                    int k_y = Gy[ky + 1][kx + 1];

                    sumRx += p.r * k_x;
                    sumGx += p.g * k_x;
                    sumBx += p.b * k_x;

                    sumRy += p.r * k_y;
                    sumGy += p.g * k_y;
                    sumBy += p.b * k_y;
                }
            }

            int magR = static_cast<int>(std::sqrt(sumRx * sumRx + sumRy * sumRy));
            int magG = static_cast<int>(std::sqrt(sumGx * sumGx + sumGy * sumGy));
            int magB = static_cast<int>(std::sqrt(sumBx * sumBx + sumBy * sumBy));

            dst.at(x, y) = {
                static_cast<uint8_t>(std::min(255, magR)),
                static_cast<uint8_t>(std::min(255, magG)),
                static_cast<uint8_t>(std::min(255, magB))
            };
        }
    }

    auto end = std::chrono::high_resolution_clock::now();
    std::chrono::duration<double, std::milli> duration = end - start;

    dst.savePPM(outputFile);
    return duration.count();
}

int main() {
    std::cout << "==============================================================\n";
    std::cout << "  SERVICO C++ DE PROCESSAMENTO DE IMAGENS - NUVEM IaaS       \n";
    std::cout << "==============================================================\n";

    // 1. Leitura de Variaveis de Ambiente Obrigatorias
    const char* envPort = std::getenv("PORT");
    int port = envPort ? std::stoi(envPort) : 8080;

    const char* envThreads = std::getenv("NUM_THREADS");
    int threads = envThreads ? std::stoi(envThreads) : 4;

    const char* envRes = std::getenv("BENCHMARK_RES");
    int benchmarkRes = envRes ? std::stoi(envRes) : 2048;

    #ifdef _OPENMP
    omp_set_num_threads(threads);
    std::cout << "[+] OpenMP ativado com " << threads << " threads configuradas via $NUM_THREADS.\n";
    #endif

    std::cout << "[+] Variavel de Ambiente PORT: " << port << "\n";
    std::cout << "[+] Variavel de Ambiente BENCHMARK_RES: " << benchmarkRes << "x" << benchmarkRes << "\n";

    #ifdef _WIN32
    WSADATA wsaData;
    WSAStartup(MAKEWORD(2, 2), &wsaData);
    #endif

    // 2. Criacao do Socket TCP Nativo do Servico
    int server_fd = socket(AF_INET, SOCK_STREAM, 0);
    if (server_fd < 0) {
        std::cerr << "[-] Erro critico ao criar socket.\n";
        return 1;
    }

    int opt = 1;
    #ifdef _WIN32
    setsockopt(server_fd, SOL_SOCKET, SO_REUSEADDR, (const char*)&opt, sizeof(opt));
    #else
    setsockopt(server_fd, SOL_SOCKET, SO_REUSEADDR, &opt, sizeof(opt));
    #endif

    sockaddr_in address;
    address.sin_family = AF_INET;
    address.sin_addr.s_addr = INADDR_ANY;
    address.sin_port = htons(port);

    if (bind(server_fd, (struct sockaddr*)&address, sizeof(address)) < 0) {
        std::cerr << "[-] Falha ao fazer bind na porta " << port << ". Verifique permissoes.\n";
        return 1;
    }

    if (listen(server_fd, 10) < 0) {
        std::cerr << "[-] Falha ao colocar socket em estado listen.\n";
        return 1;
    }

    std::cout << "[*] SERVICO PRONTO E ESCUTANDO NA PORTA: " << port << "\n";
    std::cout << "[*] Endpoints Disponiveis:\n";
    std::cout << "    - GET  /health   -> Verifica saude do servico\n";
    std::cout << "    - POST /process  -> Dispara convolucao pesada de imagens\n";
    std::cout << "--------------------------------------------------------------\n";

    // 3. Loop de Atendimento de Requisicoes HTTP
    while (true) {
        sockaddr_in client_addr;
        #ifdef _WIN32
        int addrlen = sizeof(client_addr);
        int client_socket = accept(server_fd, (struct sockaddr*)&client_addr, &addrlen);
        #else
        socklen_t addrlen = sizeof(client_addr);
        int client_socket = accept(server_fd, (struct sockaddr*)&client_addr, &addrlen);
        #endif

        if (client_socket < 0) {
            continue;
        }

        char buffer[2048] = {0};
        #ifdef _WIN32
        recv(client_socket, buffer, sizeof(buffer) - 1, 0);
        #else
        read(client_socket, buffer, sizeof(buffer) - 1);
        #endif

        std::string request(buffer);
        std::string responseBody;
        std::string statusCode = "200 OK";

        if (request.find("GET /health") != std::string::npos || request.find("GET / ") != std::string::npos) {
            responseBody = "{\n"
                           "  \"status\": \"healthy\",\n"
                           "  \"service\": \"cpp-image-processor-iaas\",\n"
                           "  \"port\": " + std::to_string(port) + ",\n"
                           "  \"threads\": " + std::to_string(threads) + ",\n"
                           "  \"model\": \"IaaS - Ambiente 100% gerenciado pelo desenvolvedor\"\n"
                           "}\n";
        } else if (request.find("/process") != std::string::npos) {
            std::cout << "[*] Requisicao de processamento recebida! Iniciando convolucao...\n";
            double elapsedMs = runHeavyProcessing(benchmarkRes, benchmarkRes, "output_sobel_processed.ppm");
            double mp = (benchmarkRes * benchmarkRes) / 1000000.0;
            double throughput = mp / (elapsedMs / 1000.0);

            responseBody = "{\n"
                           "  \"status\": \"completed\",\n"
                           "  \"resolution\": \"" + std::to_string(benchmarkRes) + "x" + std::to_string(benchmarkRes) + "\",\n"
                           "  \"pixels_processed\": " + std::to_string(benchmarkRes * benchmarkRes) + ",\n"
                           "  \"time_ms\": " + std::to_string(elapsedMs) + ",\n"
                           "  \"throughput_mpps\": " + std::to_string(throughput) + ",\n"
                           "  \"output_file\": \"output_sobel_processed.ppm\"\n"
                           "}\n";
            std::cout << "[+] Processamento concluido em " << elapsedMs << " ms.\n";
        } else {
            statusCode = "404 Not Found";
            responseBody = "{\"error\": \"Endpoint nao encontrado. Use /health ou /process\"}\n";
        }

        std::string httpResponse = "HTTP/1.1 " + statusCode + "\r\n"
                                   "Content-Type: application/json\r\n"
                                   "Content-Length: " + std::to_string(responseBody.size()) + "\r\n"
                                   "Connection: close\r\n\r\n" + responseBody;

        #ifdef _WIN32
        send(client_socket, httpResponse.c_str(), httpResponse.size(), 0);
        closesocket(client_socket);
        #else
        write(client_socket, httpResponse.c_str(), httpResponse.size());
        close(client_socket);
        #endif
    }

    #ifdef _WIN32
    closesocket(server_fd);
    WSACleanup();
    #else
    close(server_fd);
    #endif

    return 0;
}
