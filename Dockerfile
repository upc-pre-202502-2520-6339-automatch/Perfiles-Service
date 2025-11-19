FROM ubuntu:latest
LABEL authors="Nicolas Vera"

ENTRYPOINT ["top", "-b"]