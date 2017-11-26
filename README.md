URL Shortener API
-----------------

Restful endpoint that takes a URL and provides a shorter URL.
When resolved, that URL redirects the user to the original URL.

Run the tests:
$ mvn test

How to start the app:
$ mvn spring-boot:run

Example:
curl -H "Content-Type: application/json" -X POST -d '{"url": "https://www.dn.se/nyheter/varlden/installda-flyg-pa-bali-pa-grund-av-rok-fran-vulkan/"}' http://localhost:8080/shortener -v
The shortened URL will be in the response header Location.

The shortened URL will redirect to the original URL.