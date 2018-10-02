# cft
Prerequisites

Make sure you have following prerequisites installed
- JDK 1.8.x
- Git
- Apache Maven 3.x

Run tests:
 - clone this repo
 - open project folder in console and run:
   * test.cmd under Windows
   * test.sh under Linux/OSX (make sure that test.sh has executable permission, if not, do `chmod +x ./test.sh`)
   * please use corresponding webdriver: geckodriver.exe for Windows, geckodriver for Mac, and geckodriver_linux for linux (can be found at the `driver` folder)
   * use `chmod +x driver/gechodriver` to make driver runnable