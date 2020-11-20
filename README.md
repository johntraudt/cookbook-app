# BUILD-A-COOKBOOK







### Setup Instructions

- Download and unzip Source Code
- Open `Server` in IntelliJ
  - Edit Configurations (App, JUnit, Maven)
    - MY_COOKBOOK_DB_URL 
    - MY_COOKBOOK_DB_USERNAME
    - MY_COOKBOOK_DB_PASSWORD
- Open `Client` in VsCode
  - npm
    - WINDOWS: `($env:REACT_APP_URL = "http://buildacookbook-env.eba-mdnbwpd5.us-east-2.elasticbeanstalk.com") -and (npm start)`
    - MAC: `REACT_APP_URL=http://buildacookbook-env.eba-mdnbwpd5.us-east-2.elasticbeanstalk.com npm start`
