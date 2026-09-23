// exemplo gerado pelo chatGPT
#include <iostream>
#include <vector>

int main() {
  std::vector<int> myvector;
  int sum(0);
  myvector.push_back(100);
  myvector.push_back(200);
  myvector.push_back(300);

  while (!myvector.empty()) {
    std::cout << myvector.back() << " ";
    sum += myvector.back();
    myvector.pop_back(); // tipo void
  }

  std::cout << "\nThe elements of myvector add up to " << sum << '\n';

  return 0;
}