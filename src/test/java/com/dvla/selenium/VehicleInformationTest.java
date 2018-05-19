package com.dvla.selenium;

import com.dvla.pages.ConfirmationPage;
import com.dvla.pages.Homepage;
import com.dvla.pages.InquiryPage;
import com.dvla.service.fileUtil.ExcelUtil;
import com.dvla.service.FileInfo;
import com.dvla.service.FileService;
import org.apache.commons.io.FileUtils;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.assertTrue;

/**
 * This class implements a test flow for
 * retrieving Vehicle Information from an excel file and asserts the info on DVLA site
 */
public class VehicleInformationTest extends TestBase {

    private WebDriver driver;
    private WebDriverWait wait;
    private ConfirmationPage confirmationPage;
    private InquiryPage inquiryPage;
    private Homepage homepage;
    private ExcelUtil excelUtil;
    private String pathToFolder = "resources";
    private String fileName = "VehicleData.xlsx";
    private String sheetName = "Sheet1";
    private int timeoutSecs = 15;
    private String baseUrl = "https://www.gov.uk/get-vehicle-information-from-dvla";

    @Before
    public void setup() {
        driver = createDriver();
        driver.manage().timeouts().implicitlyWait(timeoutSecs, TimeUnit.SECONDS);
        wait = new WebDriverWait(driver, timeoutSecs);
        driver.get(baseUrl);

        confirmationPage = new ConfirmationPage(driver, timeoutSecs);
        inquiryPage = new InquiryPage(driver, timeoutSecs);
        homepage = new Homepage(driver, timeoutSecs);
        excelUtil = new ExcelUtil();

    }

    /**
     * Reads vehicle plate number from an excel file and verifies the make and colour,
     * stores screenshots in a directory
     *
     * @throws IOException
     * @throws InvalidFormatException
     * @see ExcelUtil
     */

    @Test
    public void testVehicleInformation() throws IOException {

        // Retrieve the file
        FileService resource = new FileService(pathToFolder);
        List<String> supportedTypes = new ArrayList<String>();
        supportedTypes.add("xlsx");
        List<FileInfo> files = resource.getSupportedFiles(supportedTypes);

        for (FileInfo fileInfo : files) {
            if (fileInfo.getName().equals(fileName)) {
                String fullFilePath = pathToFolder + "/" + fileInfo.getName();
                excelUtil.setExcelFile(fullFilePath, sheetName);
            } else {
                return;
            }

            assertTrue("Start button is not displayed", homepage.START_BUTTON.isDisplayed());
            homepage.clickOnStartButton();

            // Read the file
            int rowCount = excelUtil.getRowCount(sheetName);
            for (int rowNum = 1; rowNum < rowCount; rowNum++) {
                int colNum = 0; // column number
                String cellData = excelUtil.getCellData(rowNum, colNum);

                // Enter registration number
                assertTrue("Registration number text area is not displayed", inquiryPage.QUERY_TEXTAREA.isDisplayed());
                inquiryPage.enterRegistrationNumber(cellData);

                // Submit the registration number
                assertTrue("Submit button is not displayed", inquiryPage.QUERY_SUBMIT_BUTTON.isDisplayed());
                inquiryPage.submitRegistrationNumber();

                // Verify expected vehicle is found
                WebElement confirmationMessage = wait
                        .until(ExpectedConditions.visibilityOf(confirmationPage.CONFIRMATION_QUESTION));
                assertTrue("Vehicle details could not be found", confirmationMessage.isDisplayed());

                // Verify make and colour of the vehicle
                colNum++;
                verifyMake(rowNum, colNum);
                colNum++;
                verifyColour(rowNum, colNum);

                // Take a screenshot of output
                captureScreen(rowNum);

                // Navigate back to inquiry page for the next vehicle check
                confirmationPage.navigate_back();

            }
        }

    }

    @After
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

    private void verifyColour(int rowNum, int colNum) {
        String colourInFile = excelUtil.getCellData(rowNum, colNum);
        String colourOnPage = confirmationPage.VEHICLE_COLOUR.getText();
        assertTrue(colourOnPage + " does not match " + colourInFile,
                colourOnPage.trim().contains(colourInFile.trim()));
    }

    private void verifyMake(int rowNum, int colNum) {
        String makeOnFile = excelUtil.getCellData(rowNum, colNum);
        String makeOnPage = confirmationPage.VEHICLE_MAKE.getText();
        assertTrue(makeOnPage + " does not match " + makeOnFile,
                makeOnPage.trim().contains(makeOnFile.trim()));
    }

    private void captureScreen(int rowNum) throws IOException {
        File scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        FileUtils.copyFile(scrFile, new File("screenshots/screenshot" + rowNum + ".png"));
    }


}