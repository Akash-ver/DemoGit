package rahulshettyacademy.TestComponents;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import rahulshettyacademy.resources.ExtentReporterNG;

import java.io.IOException;

public class Listeners extends BaseTest implements ITestListener {
    ExtentTest test;
    ExtentReports extent=ExtentReporterNG.getReportObject();
    ThreadLocal<ExtentTest> extentTest=new ThreadLocal<ExtentTest>();//Thread safe
    @Override
    public void onTestStart(ITestResult result) {
        // not implemented
    test=extent.createTest(result.getMethod().getMethodName());
    extentTest.set(test);//unique thread id(ErrorValidationTest)->test
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        // not implemented
        extentTest.get().log(Status.PASS,"Test Passed");
    }

    @Override
    public void onTestFailure(ITestResult result) {
        // not implemented
         extentTest.get().fail(result.getThrowable());
        try {
            driver=(WebDriver) result.getTestClass().getRealClass().getField("driver")
                    .get(result.getInstance());
        } catch (Exception e1) {
            e1.printStackTrace();
        }
        String filePath=null;
        try {
            filePath = String.valueOf(getScreenshot(result.getMethod().getMethodName(),driver));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        extentTest.get().addScreenCaptureFromPath(filePath,result.getMethod().getMethodName());
        //Screenshot,Attach to report
    }

   @Override
    public void onTestSkipped(ITestResult result) {
        // not implemented
    }
    @Override
    public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
        // not implemented
    }
    @Override
    public void onTestFailedWithTimeout(ITestResult result) {
        onTestFailure(result);
    }

    @Override
    public void onStart(ITestContext context) {
        // not implemented
    }

    @Override
    public void onFinish(ITestContext context) {
        // not implemented
        extent.flush();
    }
}
