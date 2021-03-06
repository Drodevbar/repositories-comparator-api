### Repositories comparator API

---

Just a simple WEB API written in Java 11 in order to practise some _Java things_, good coding practises, testing (Unit & Integration), Docker and CI from GitHub. 

The service offers functionality of acquaring information about repository, such as: forsks/stars/watchers number, open/closed/merged pull requests number and latest release date.

The service now only supports getting information about GitHub repositories. Although it has a proper layer of abstraction, in order to easily enable adding other repositories' providers, such as: GitLab, BitBucket etc.

### Tools:
- Java 11
- Spring Boot
- JUnit, AssertJ, Mockito, WireMock
- Maven
- Docker
- GitHub Actions (CI)

### Installation
(*) _In order to run this service, you need to have `Docker` installed on your machine_

The project contains simple bash script `deploy.sh`.
The script will check if Docker is installed and Docker daemon is running on your machine.
If so, it will build the image and run the container on the specified `port`.
If port is not specified, it defaults to `9090`. 

```bash
cd <project-dir>
bash deploy.sh [port]
```

### Api documentation

(*) _Api has only one business related endpoint (for simplicity sake)_

|#|ENDPOINT|PARAMETERS|METHOD|
|---|---|---|---|
|1|/api/repository/{owner}/{name}|owner - (string, required) owner of the repository <br> name - (string, required) name of the repository|GET|

If given repository was found, API will respond with `200` status code and JSON body containing repository data, for example:
```JSON
{
  "forks_number": 1237,
  "stars_number": 12022,
  "watchers_number": 12022,
  "latest_release_date": "2014-01-27T08:49:24Z",
  "open_pull_requests_number": 1,
  "closed_pull_requests_number": 1187,
  "merged_pull_requests_number": 1002
}
```

If given repository was not found, API will respond with `404` status code and JSON body containing message key, for example:
```JSON
{
  "message": "Given repository was not found"
}
```

---

|#|ENDPOINT|PARAMETERS|METHOD|
|---|---|---|---|
|2|/admin/ping||GET|

A simple endpoint to check if application responds. Expacted response is "pong" in plain TEXT format.
