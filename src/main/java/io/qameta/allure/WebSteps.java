package io.qameta.allure;

import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.fail;

/**
 * @author eroshenkoam (Artem Eroshenko).
 */
public class WebSteps {

    @Step("Starting web driver")
    public void startDriver() {
        maybeThrowSeleniumTimeoutException();
    }

    @Step("Stopping web driver")
    public void stopDriver() {
        maybeThrowSeleniumTimeoutException();
    }

    @Step("Open issues page `{owner}/{repo}`")
    public void openIssuesPage(final String owner, final String repo) {
        attachPageSourceIssueList();
        attachScreenshotIssueList();
        maybeThrowElementNotFoundException();
    }

    @Step("Open pull requests page `{owner}/{repo}`")
    public void openPullRequestsPage(final String owner, final String repo) {
        attachPageSourcePrList();
        attachScreenshotPrList();
        maybeThrowElementNotFoundException();
    }

    @Step("Create pull request from branch `{branch}`")
    public void createPullRequestFromBranch(final String branch) {
        maybeThrowElementNotFoundException();
    }

    @Step("Create issue with title `{title}`")
    public void createIssueWithTitle(String title) {
        attachPageSourceIssueCreateForm();
        attachScreenshotIssueCreateForm();
        maybeThrowAssertionException(title);
    }

    @Step("Close pull request for branch `{branch}`")
    public void closePullRequestForBranch(final String branch) {
        maybeThrowAssertionException(branch);
    }

    @Step("Close issue with title `{title}`")
    public void closeIssueWithTitle(final String title) {
        maybeThrowAssertionException(title);
    }

    @Step("Check pull request for branch `{branch}` exists")
    public void shouldSeePullRequestForBranch(final String branch) {
        maybeThrowAssertionException(branch);
    }

    @Step("Check issue with title `{title}` exists")
    public void shouldSeeIssueWithTitle(final String title) {
        maybeThrowAssertionException(title);
    }

    @Step("Check pull request for branch `{branch}` not exists")
    public void shouldNotSeePullRequestForBranch(final String branch) {
        maybeThrowAssertionException(branch);
    }

    @Step("Check issue with title `{title}` not exists")
    public void shouldNotSeeIssueWithTitle(final String title) {
        maybeThrowAssertionException(title);
    }

    @Attachment(value = "Issue Create Form", type = "text/html", fileExtension = "html")
    public byte[] attachPageSourceIssueCreateForm() {
        return addAttachment("issue-create-form.html");
    }

    @Attachment(value = "Screenshot", fileExtension = "png")
    public byte[] attachScreenshotIssueCreateForm() {
        return addAttachment("issue-create-form.png");
    }

    @Attachment(value = "Issue List", type = "text/html", fileExtension = "html")
    public byte[] attachPageSourceIssueList() {
        return addAttachment("issue-list.html");
    }

    @Attachment(value = "Screenshot", fileExtension = "png")
    public byte[] attachScreenshotIssueList() {
        return addAttachment("issue-list.png");
    }

    @Attachment(value = "Pull Request List", type = "text/html", fileExtension = "html")
    public byte[] attachPageSourcePrList() {
        return addAttachment("pr-list.html");
    }

    @Attachment(value = "Screenshot", fileExtension = "png")
    public byte[] attachScreenshotPrList() {
        return addAttachment("pr-list.png");
    }

    private byte[] addAttachment(final String resourceName) {
        try (InputStream is = getClass().getClassLoader().getResourceAsStream(resourceName)) {
            if (Objects.isNull(is)) {
                throw new IllegalStateException("could not find resource");
            }
            return IOUtils.toByteArray(is);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void maybeThrowSeleniumTimeoutException() {
        if (isTimeToThrowException()) {
            fail(webDriverIsNotReachable("Allure"));
        }
    }

    private void maybeThrowElementNotFoundException() {
        try {
            Thread.sleep(1000);
            if (isTimeToThrowException()) {
                fail(elementNotFoundMessage("[//div[@class='something']]"));
            }
        } catch (InterruptedException e) {
            //do nothing, it's dummy test
        }
    }

    private void maybeThrowAssertionException(String text) {
        if (isTimeToThrowException()) {
            fail(textEqual(text, "another text"));
        }
    }

    private boolean isTimeToThrowException() {
        return new Random().nextBoolean()
               && new Random().nextBoolean()
               && new Random().nextBoolean()
               && new Random().nextBoolean();
    }

    private String webDriverIsNotReachable(final String text) {
        return String.format("WebDriverException: chrome not reachable\n" +
                             "Element not found {By.xpath: //a[@href='./allure-demo']}\n" +
                             "Expected: text '%s'\n" +
                             "Page source: file:./allure-demo/build/reports/tests/1603973861960.0.html\n" +
                             "Timeout: 4 s.", text);
    }

    private String textEqual(final String expected, final String actual) {
        return String.format("Element should text '%s' {By.xpath: //a[@href='./allure-demo']}\n" +
                             "Element: '<a class=\"v-align-middle\">%s</a>'\n" +
                             "Screenshot: file:./allure-demo/build/reports/tests/1603973703632.0.png\n" +
                             "Page source: file:./allure-demo/build/reports/tests/1603973703632.0.html\n" +
                             "Timeout: 4 s.\n", expected, actual);
    }

    private String elementNotFoundMessage(final String selector) {
        return String.format("Element not found {By.xpath: %s}\n" +
                             "Expected: visible or transparent: visible or have css value opacity=0\n" +
                             "Screenshot: file:./allure-demo/build/reports/tests/1603973516437.0.png\n" +
                             "Page source: file:./allure-demo/build/reports/tests/1603973516437.0.html\n" +
                             "Timeout: 4 s.\n", selector);
    }
}
