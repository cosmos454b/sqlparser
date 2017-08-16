SELECT a.name, 
       b.lat, 
       c.currency 
FROM   table1 a 
       LEFT JOIN (SELECT DISTINCT lat 
                  FROM   geocodes) b 
              ON ( a.id = b.id ) 
       LEFT JOIN currency_codes c 
              ON ( c.id = b.id ) 
 WHERE a.country="abc"
LIMIT  5