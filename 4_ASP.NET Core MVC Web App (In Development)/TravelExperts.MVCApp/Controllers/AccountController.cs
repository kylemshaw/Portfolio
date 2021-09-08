using Microsoft.AspNetCore.Authentication;
using Microsoft.AspNetCore.Authorization;
using Microsoft.AspNetCore.Mvc;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Security.Claims;
using System.Threading.Tasks;
using TravelExperts.BLL;
using TravelExperts.Data.Domain;

namespace TravelExperts.MVCApp.Controllers
{
    public class AccountController : Controller
    {
        //GET
        [HttpGet]
        public IActionResult Login(string returnUrl = null)
        {
            //tempdata is the only place that survices multiple round trips (viewbag and the other one do not)
            if (returnUrl != null)
            {
                TempData["ReturnUrl"] = returnUrl; //save for redirect after successful login
            }
            return View();
        }

        //POST
        [HttpPost]
        public async Task<IActionResult> LoginAsync(Authentication user)
        {

            Authentication authUser = AuthenticationsManager.Authenticate(user.Username, user.Password);
            if (authUser == null)//did not authenticate
            {
                return View();//stay on login page
            }
            else //successful authentication
            {
                List<Claim> claims = new List<Claim>()
                {
                    new Claim(ClaimTypes.Name, authUser.Username), //use predefined claim
                    new Claim("FirstName", authUser.Customer.CustFirstName), //define custom claim
                    new Claim("CustomerId", authUser.Customer.CustomerId.ToString()), //define custom claim
                    new Claim("AuthId", authUser.Id.ToString())
                    //new Claim(ClaimTypes.Role, authUser.Role)
                };

                ClaimsIdentity claimsIdentity = new ClaimsIdentity(claims, "Cookies"); //(claims, authentication type)

                ClaimsPrincipal principal = new ClaimsPrincipal(claimsIdentity);

                //async sign in function with prinicpal information using cookies
                await HttpContext.SignInAsync("Cookies", principal);

                //redirect from login page
                if (TempData["ReturnUrl"] != null)
                {
                    string returnUrl = TempData["ReturnUrl"].ToString(); //hold return url so we can clear tempdata before return
                    TempData["ReturnUrl"] = null;//reset temp data so next call to Login defaults to home
                    return Redirect(returnUrl); //TempData stores everything as generic objects so have to change it
                    
                }                    
                else
                {
                    return RedirectToAction("Index", "Home");
                }

            }
        }//LoginAsync

        public async Task<IActionResult> LogoutAsync()
        {
            await HttpContext.SignOutAsync("Cookies"); //release authentication ticket (destroy cookie)
            return RedirectToAction("Index", "Home");
        }

        public IActionResult AccessDenied()
        {
            return View();
        }

        // GET: AccountController/Create
        public ActionResult Create(string regReturnUrl = null)
        {
            //tempdata is the only place that survices multiple round trips (viewbag and the other one do not)
            if (regReturnUrl != null)
            {
                TempData["RegReturnUrl"] = regReturnUrl; //save for redirect after successful login
            }
            return View();
        }

        // POST: AccountController/Create
        [HttpPost]
        [ValidateAntiForgeryToken]
        public ActionResult Create(Authentication auth)
        {
            if(ModelState.IsValid)
            {
                //Bussiness phone and email can be null in database so set appropriatly
                if (auth.Customer.CustBusPhone == null)
                    auth.Customer.CustBusPhone = "";
                if (auth.Customer.CustEmail == null)
                    auth.Customer.CustEmail = "";

                try
                {
                    //add to database
                    AuthenticationsManager.Add(auth);

                    //redirect to page where register was clicked (if set)
                    if (TempData["RegReturnUrl"] != null)
                    {
                        string regReturnUrl = TempData["RegReturnUrl"].ToString(); //hold return url so we can clear temp data before return
                        TempData["RegReturnUrl"] = null;//reset temp data so next call to Login defaults to home
                        return RedirectToAction("Login", "Account", new { returnUrl = regReturnUrl });

                    }
                    else //return url not set so just log in with no return url
                    {
                        return RedirectToAction("Login");
                    }

                }
                catch
                {
                    return View();
                }
            }
            else 
            {
                return View();
            }
            
            //if (ModelState.IsValid)
            //{
            //    cnfrm
            //}
            
        }

        [Authorize]
        // GET: AccountsController/Edit/5
        public ActionResult Edit(int id)
        {
            int authId = Convert.ToInt32(User.Claims.FirstOrDefault(c => c.Type == "AuthId").Value);
            if(authId == id)
            {
                // get authentication with this id and pass to the view
                Authentication auth = AuthenticationsManager.Find(id);
                return View(auth);
            }
            else
            {
                return Redirect("/error");
            }
            
        }

        // POST: AccountController/Edit/5
        [HttpPost]
        [ValidateAntiForgeryToken]
        public ActionResult Edit(int id, Authentication auth) // new data
        {
            if (ModelState.IsValid)
            {
                //Bussiness phone and email can be null in database so set appropriatly
                if (auth.Customer.CustBusPhone == null)
                    auth.Customer.CustBusPhone = "";
                if (auth.Customer.CustEmail == null)
                    auth.Customer.CustEmail = "";

                try
                {
                    AuthenticationsManager.Update(auth);
                    return RedirectToAction("Index", "Home");
                }
                catch
                {
                    return View();
                }
            }
            else
            {
                return View();
            }

        }
    }
}
