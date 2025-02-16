# FoodicsTask - API Automation

## Overview

FoodicsTask is an automated API testing framework built using Java, TestNG, and RestAssured. The tests focus on user management endpoints provided by ReqRes.in. The framework demonstrates how to:
- Create a new user
- Retrieve user details
- Update user information

This repository also uses a properties file to externalize configuration (such as base URL and API endpoints) and employs Java Faker to generate realistic test data.

How It Works

SetUpRequests.java: Builds a RestAssured RequestSpecification using the base URL and common headers.
ReadPropertiesFile.java: Reads configuration properties from config.properties.
BaseTest.java: Contains global variables (e.g., userId, userName, userJob) and common setup logic.
CreateUserTest.java, RetrieveUserTest.java, UpdateUserTest.java: Implement the API tests with assertions to validate responses.

