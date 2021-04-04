# Transaction Statistics Service
_________________________________________________
RESTful API to to calculate realtime statistics for the last 60 seconds of transactions.

### URL
* ***/transactions***

### Method
* `POST` | `DELETE`
    ####Create Transactions
    `POST /transactions` endpoint is called to create a new transaction. It executes in constant time and memory (O(1)).
  
    **Request Body**
    ````json
    {
	    "amount": "30",
	    "timestamp": "2021-04-04T11:37:35.312Z"
    }
    ````
    Where
  
        * amount -  transaction amount; a string of arbitrary length that is parsable as a BigDecimal
        * timestamp - transaction time in the ISO 8601 format YYYY-MM-DDThh:mm:ss.sssZ in the UTC timezone (this is not the current timestamp)
    
    **Response**
    ````
    Empty body with one of the following:
        201 – in case of success
        204 – if the transaction is older than 60 seconds
        400 – if the JSON is invalid
        422 – if any of the fields are not parsable or the transaction date is in the future
    ````
  
    ####Delete Transactions
    `DELETE /transactions` This endpoint causes all existing transactions to be deleted. The endpoint should accept an empty request body and return a 204 status code.

### URL
* ***/statistics***

### Method
* `GET`
    ####Get realtime statistics
    `GET /statistics` endpoint returns the statistics based on the transactions that happened in the last 60 seconds. It MUST execute in constant time and memory (O(1)).
  
    **Response**
    ````json
    {
        "sum": "60.00",
        "avg": "20.00",
        "max": "30.00",
        "min": "10.00",
        "count": 3
    }
    ````
    Where
  
        ● sum – a BigDecimal specifying the total sum of transaction value in the last 60 seconds
  
        ● avg – a BigDecimal specifying the average amount of transaction value in the last 60 seconds
     
        ● max – a BigDecimal specifying single highest transaction value in the last 60 seconds
        
        ● min – a BigDecimal specifying single lowest transaction value in the last 60 seconds
        
        ● count – a long specifying the total number of transactions that happened in the last 60 seconds

        * Note - All BigDecimal values always contain exactly two decimal places and use `HALF_ROUND_UP` 
                 rounding. eg: 10.345 is returned as 10.35, 10.8 is returned as 10.80