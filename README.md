# cucumber-ui-framework
Booking.com UI scenarios are automated using Cucumber framework

1. Install **Maven** and Import as Maven project in IntelliJ or Eclipse

2. **File Paths:**
     - **_Features:_** ./src/test/resources/features
     - **_Step Definitions:_** ./src/test/java/steps
     - **_Pages:_**  ./src/test/java/pages

3. 76.0 Chrome driver version is in use https://chromedriver.storage.googleapis.com/index.html?path=76.0.3809.68/
    Please check compatibility with system and replace chrome driver under drivers folder with

4. **Command to run all scenarios:**
    ```bash
    mvn clean verify
    ```

5. **Run Time Parameters:**
     - **_Mandatory Parameters:_**
    ```bash
    -DbaseUrl="**Pass Application Url here**"
    ```
    ```
     - **_Optional Parameters:_**
    ```bash With Tags
    -DcucumberTags="**Pass Cucumber Tag and combinations here**"
    ```
    ```bash Parallel Run
    -DforkCount="**Pass number of parallel threads here**"
    ```
    
    ```bash
    -DdriverTimeout="**Pass timeout secs to override default timeout 10**"
    ```
    ```bash Headless run
    -DbrowserMode="** Pass headless or normal here**"
    ```
    ```bash
    -Dfailsafe.rerunFailingTestsCount="**Pass number of re runs during failure here**"
    ```
    ```
    ```bash Screenshots
    -DscreenShot="**Pass failure or allSteps here**"
    ```
   

6. **Examples:**
    ```bash
    mvn clean verify -DbaseUrl="https://www.booking.com/" -DcucumberTags="@hotelSearch" 
    ```
    ```bash
    Parallel Testing
    mvn clean verify -DbaseUrl="https://www.booking.com/" -DcucumberTags="@carSearch" -DforkCount=2 -DdriverTimeout=30
    ```
    ```bash
    -Dfailsafe.rerunFailingTestsCount=1
    ```
    ```bash Headless Testing
    -DbrowserMode="headless"
    ```

7. **Report Generation command:**
    ```bash
    mvn allure:serve
    ```
    For Windows, 
    
    In order to generate a report, we should install Allure command-line interpreter.
    
    Download the latest version as a zip archive from bintray: https://bintray.com/qameta/generic/allure2/2.7.0#files/io%2Fqameta%2Fallure%2Fallure%2F2.7.0
    Unpack the archive.
    Navigate to the bin directory.
    Add allure’s bin directory to system Path variable.
