package io.qameta.allure;

import static io.qameta.allure.Allure.step;

public class RestSteps {

    @Step("Create issue with title `{title}`")
    public void createIssueWithTitle(final String owner, final String repo, final String title) {
        Allure.addAttachment("Request", "application/json", "{\n"
                                                            + "  \"title\": \"Bug Report: Application crashes on data import\",\n"
                                                            + "  \"body\": \"There's a recurring issue where the application crashes when importing large data sets. This seems to happen consistently with files over 100MB. Steps to reproduce: 1. Open the data import tool. 2. Attempt to import a file larger than 100MB. Expected behavior: The file should import successfully. Actual behavior: The application crashes with an error message about memory allocation.\",\n"
                                                            + "  \"labels\": [\"bug\", \"high priority\", \"needs review\"],\n"
                                                            + "  \"assignees\": [\"developer1\", \"developer2\"],\n"
                                                            + "  \"milestone\": 2,\n"
                                                            + "  \"comments\": [\n"
                                                            + "    {\n"
                                                            + "      \"user\": \"manager\",\n"
                                                            + "      \"comment\": \"This needs urgent attention. Affecting key clients.\"\n"
                                                            + "    },\n"
                                                            + "    {\n"
                                                            + "      \"user\": \"developer1\",\n"
                                                            + "      \"comment\": \"Started working on this, looking into memory management issues.\"\n"
                                                            + "    }\n"
                                                            + "  ]\n"
                                                            + "}\n");
        step(String.format("POST /repos/%s/%s/issues", owner, repo));
    }

    @Step("Close issue with title `{title}`")
    public void closeIssueWithTitle(final String owner, final String repo, final String title) {
        step(String.format("GET /repos/%s/%s/issues?text=%s", owner, repo, title));
        step(String.format("PATCH /repos/%s/%s/issues/%s", owner, repo, 10));
    }

    @Step("Check note with content `{title}` exists")
    public void shouldSeeIssueWithTitle(final String owner, final String repo, final String title) {
        step(String.format("GET /repos/%s/%s/issues?text=%s", owner, repo, title));
        step(String.format("GET /repos/%s/%s/issues/%s", owner, repo, 10));
    }

}
