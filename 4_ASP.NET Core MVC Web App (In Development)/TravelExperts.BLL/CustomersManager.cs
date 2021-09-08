using Microsoft.EntityFrameworkCore;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using TravelExperts.Data.Domain;

namespace TravelExperts.BLL
{
    /// <summary>
    /// Handles CRUD Operations on the Authtication Table
    /// </summary>
    
    public static class CustomersManager
    {

        public static Authentication Authenticate(string username, string password)
        {
            TravelExpertsContext db = new TravelExpertsContext();
            Authentication auth = db.Authentications.Include(a => a.Customer).
              SingleOrDefault(a => a.Username == username && a.Password == password);

            return auth;
        }
        /// <summary>
        /// finds authentication record for customer with given ID
        /// </summary>
        /// <param name="customerId">customer ID to find</param>
        /// <returns>authentication record or null if not found</returns>
        public static Authentication Find(int customerId)
        {
            TravelExpertsContext db = new TravelExpertsContext(); // context object
            Authentication auth = db.Authentications.
            SingleOrDefault(a => a.CustomerId == customerId);
            return auth;
        }

        /// <summary>
        /// add authentication record to the table
        /// </summary>
        /// <param name="auth">record to add</param>
        public static void Add(Authentication auth)
        {
            TravelExpertsContext db = new TravelExpertsContext();
            db.Authentications.Add(auth); // then add record to Authentication
            db.SaveChanges();
        }

        /// <summary>
        /// update authentication record
        /// </summary>
        /// <param name="auth">new data for update</param>
        public static void Update(Customer auth)
        {
            TravelExpertsContext db = new TravelExpertsContext();
            // find the existing record in the context based on ID
            Authentication authFromContext = db.Authentications.
            SingleOrDefault(a => a.Id == auth.CustomerId);
            // replace old values with new values
            //authFromContext.Username = auth.Authentications.Username;
            //authFromContext.Password = auth.Authentications.Password;
            authFromContext.Customer.CustFirstName = auth.CustFirstName;
            authFromContext.Customer.CustLastName = auth.CustLastName;
            authFromContext.Customer.CustHomePhone = auth.CustHomePhone;
            authFromContext.Customer.CustCity = auth.CustCity;
            authFromContext.Customer.CustProv = auth.CustProv;
            authFromContext.Customer.CustPostal = auth.CustPostal;
            authFromContext.Customer.CustCountry = auth.CustCountry;
            authFromContext.Customer.CustBusPhone = auth.CustBusPhone;
            authFromContext.Customer.CustEmail = auth.CustEmail;
            db.SaveChanges();
        }

        public static List<Customer> GetAll()
        {
            TravelExpertsContext db = new TravelExpertsContext();
            List<Customer> customers = db.Customers.OrderBy(o => o.CustFirstName).ToList();
            return customers;
        }

        public static Customer FindCust(int id)
        {
            TravelExpertsContext db = new TravelExpertsContext();
            Customer customer = db.Customers.Find(id);
            return customer;
        }

        public static void AddNewCust(Customer customer)
        {
            TravelExpertsContext db = new TravelExpertsContext();
            db.Customers.Add(customer);
            db.SaveChanges();
        }

        public static void UpdateCust(Customer cust) //new data object
        {
            TravelExpertsContext db = new TravelExpertsContext();
            Customer originalCust = db.Customers.Find(cust.CustomerId); // old data object
            originalCust.CustFirstName = cust.CustFirstName;
            originalCust.CustLastName = cust.CustLastName;
            originalCust.CustHomePhone = cust.CustHomePhone;
            originalCust.CustCity = cust.CustCity;
            originalCust.CustProv = cust.CustProv;
            originalCust.CustPostal = cust.CustPostal;
            originalCust.CustCountry = cust.CustCountry;
            originalCust.CustBusPhone = cust.CustBusPhone;
            originalCust.CustEmail = cust.CustEmail;
            db.SaveChanges();
        }
    }
}
