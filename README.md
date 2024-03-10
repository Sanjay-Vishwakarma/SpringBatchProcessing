1- for first commit
curl --location --request POST 'http://localhost:9191/jobs/importCustomers'
----------------------------------------------

2- dynamically upload csv file
curl --location 'http://localhost:9191/jobs/importCustomers' \
--form 'file=@"/C:/Users/Sj/Desktop/customers.csv"'

