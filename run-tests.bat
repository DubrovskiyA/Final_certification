rmdir /Q /S .\allure-results
call mvn clean test
move .\allure-report\history .\allure-results\
call allure generate --clean
call allure open