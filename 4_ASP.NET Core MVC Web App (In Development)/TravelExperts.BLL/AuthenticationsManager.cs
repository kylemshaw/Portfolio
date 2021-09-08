using Microsoft.EntityFrameworkCore;
using System.Linq;
using TravelExperts.Data.Domain;

namespace TravelExperts.BLL
{
    /// <summary>
    /// Handles CRUD Operations on the Authtication Table
    /// </summary>
    public static class AuthenticationsManager
    {

        /// <summary>
        /// authenticates user by finding username and password in the database
        /// </summary>
        /// <param name="username">username to authenticate</param>
        /// <param name="password">password matching the above username</param>
        /// <returns> Authentication object if found, null otherwise</returns>
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
        /// <param name="authId"> Auth ID to find</param>
        /// <returns>authentication record or null if not found</returns>
        public static Authentication Find(int authId)
        {
            TravelExpertsContext db = new TravelExpertsContext(); 
            Authentication auth = db.Authentications.Include(a => a.Customer)
                                                    .SingleOrDefault(a => a.Id == authId);
            return auth;
        }

        /// <summary>
        /// add authentication record to the table
        /// </summary>
        /// <param name="auth">record to add</param>
        public static void Add(Authentication auth)
        {
            TravelExpertsContext db = new TravelExpertsContext();
            db.Authentications.Add(auth);
            db.SaveChanges();
        }

        /// <summary>
        /// update authentication record
        /// </summary>
        /// <param name="auth">new data for update</param>
        public static void Update(Authentication auth)
        {
           
            TravelExpertsContext db = new TravelExpertsContext();
            Authentication originalAuth = db.Authentications.Include(a => a.Customer)
                                                            .SingleOrDefault(a => a.Id == auth.Id);
            originalAuth.Username = auth.Username;
            originalAuth.Password = auth.Password;
            originalAuth.Customer.CustFirstName = auth.Customer.CustFirstName;
            originalAuth.Customer.CustLastName = auth.Customer.CustLastName;
            originalAuth.Customer.CustAddress = auth.Customer.CustAddress;
            originalAuth.Customer.CustCity = auth.Customer.CustCity;
            originalAuth.Customer.CustProv = auth.Customer.CustProv;
            originalAuth.Customer.CustPostal = auth.Customer.CustPostal;
            originalAuth.Customer.CustCountry = auth.Customer.CustCountry;
            originalAuth.Customer.CustHomePhone = auth.Customer.CustHomePhone;
            originalAuth.Customer.CustBusPhone = auth.Customer.CustBusPhone;
            originalAuth.Customer.CustEmail = auth.Customer.CustEmail;
                
            db.SaveChanges();
            
        }
    }
}
