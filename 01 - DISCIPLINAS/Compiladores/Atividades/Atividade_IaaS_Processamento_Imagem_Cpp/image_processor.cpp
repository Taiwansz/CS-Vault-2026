/**
 * ==============================================================================
 * ALGORITMO PESADO DE PROCESSAMENTO DE IMAGENS EM C++ (SOBEL & GAUSSIAN CONV)
 * Disciplina: Compiladores / Infraestrutura em Nuvem (IaaS)
 * Autor: Matheus Sousa dos Santos (Taiwansz)
 * Compilacao Recomendada (LLVM/Clang):
 *   clang++ -O3 -march=native -flto -fopenmp -std=c++17 image_processor.cpp -o image_processor
 * Compilacao Recomendada (GCC):
 *   g++ -O3 -march=native -fopenmp -std=c++17 image_processor.cpp -o image_processor
 * ==============================================================================
 */

#include <iostream>
#include <vector>
#include <cmath>
#include <chrono>
#include <fstream>
#include <string>
#include <iomanip>

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

    // Gerador de Imagem Sintetica de Teste (Carga Pesada de 16M pixels)
    static Image generateBenchmarkImage(int w, int h) {
        std::cout << "[*] Gerando imagem sintetica de alta densidade (" << w << "x" << h << " = " 
                  << (w * h) / 1000000.0 << " MP)...\n";
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

    // Exportador para formato binario PPM (P6) - Sem dependencias externas
    bool savePPM(const std::string& filename) const {
        std::ofstream file(filename, std::ios::binary);
        if (!file.is_open()) return false;

        file << "P6\n" << width << " " << height << "\n255\n";
        file.write(reinterpret_cast<const char*>(data.data()), data.size() * sizeof(Pixel));
        return true;
    }
};

// Algoritmo Pesado de Processamento: Convolucao 2D com Deteccao de Bordas Sobel e Realce de Gradiente
Image processSobelEdgeDetection(const Image& src) {
    int w = src.width;
    int h = src.height;
    Image dst(w, h);

    // Kernels de Sobel para calculo de gradientes horizontais e verticais
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

    #pragma omp parallel for schedule(dynamic, 64)
    for (int y = 1; y < h - 1; ++y) {
        for (int x = 1; x < w - 1; ++x) {
            float sumRx = 0, sumGx = 0, sumBx = 0;
            float sumRy = 0, sumGy = 0, sumBy = 0;

            // Convolucao 3x3 no espaco de cores
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

            // Magnitude do gradiente vetorial Euclidiano: sqrt(Gx^2 + Gy^2)
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

    return dst;
}

int main(int argc, char* argv[]) {
    std::cout << "==============================================================\n";
    std::cout << "  MOTOR C++ DE PROCESSAMENTO PESADO DE IMAGENS (SOBEL CONV)   \n";
    std::cout << "  Ambiente Alvo: Servidor Linux IaaS (Bare-Metal / Ubuntu)    \n";
    std::cout << "==============================================================\n";

    #ifdef _OPENMP
    std::cout << "[+] OpenMP ativado. Threads disponiveis: " << omp_get_max_threads() << "\n";
    #else
    std::cout << "[!] OpenMP desativado. Execucao Single-Thread.\n";
    #endif

    // Resolucao padrao para benchmark pesado: 4096 x 4096 = 16.7 Megapixels
    int width = 4096;
    int height = 4096;
    std::string outputFile = "output_sobel_processed.ppm";

    if (argc >= 3) {
        width = std::stoi(argv[1]);
        height = std::stoi(argv[2]);
    }

    // 1. Instanciacao da Imagem
    Image input = Image::generateBenchmarkImage(width, height);

    // 2. Processamento Pesado com Medicao Temporal
    std::cout << "[*] Executando convolucao pesada de Sobel sobre " << (width * height) << " pixels...\n";
    auto start = std::chrono::high_resolution_clock::now();

    Image output = processSobelEdgeDetection(input);

    auto end = std::chrono::high_resolution_clock::now();
    std::chrono::duration<double, std::milli> duration = end - start;

    double megapixelPerSec = ((width * height) / 1000000.0) / (duration.count() / 1000.0);

    // 3. Relatorio de Telemetria
    std::cout << "\n--- TELEMETRIA DE EXECUCAO ---\n";
    std::cout << "Tempo Total de Convolucao: " << std::fixed << std::setprecision(2) << duration.count() << " ms\n";
    std::cout << "Taxa de Processamento:     " << std::fixed << std::setprecision(2) << megapixelPerSec << " Megapixels/segundo\n";

    // 4. Gravacao do Artefato de Saida
    std::cout << "[*] Gravando resultado em disco: " << outputFile << "...\n";
    if (output.savePPM(outputFile)) {
        std::cout << "[+] Arquivo " << outputFile << " gerado com sucesso.\n";
    } else {
        std::cerr << "[-] Erro ao salvar o arquivo.\n";
        return 1;
    }

    std::cout << "[+] Concluido com exito.\n";
    return 0;
}
