# SabreFlighFinder
A demo project to search for Flight availability using the Sabre API to be deployed as a docker image in an exercise for the SUSE Cloud Native Foundations Scholarship

In order to run this app, you need to create some credentials for the SkyScanner API through RapidAPI.com @ https://rapidapi.com/skyscanner/api/skyscanner-flight-search/ and then you need to configure the appropriate properties: `skyscanner.key` and `skyscanner.host`.

You can configure those properties through one of the following options:
* Through command line properties when starting the application: `-Dskyscanner.key=**KEY** -Dskyscanner.host=**HOST**`
* Through the environment variables `SKYSCANNER_KEY` and `SKYSCANNER_HOST`
* In a docker environment, you can do it through secrets named `skyscanner.key` and `skyscanner.host` and specifying the directory where the secret files reside through the environment variable `docker-secret.bind-path` (for example `/etc/secrets`)