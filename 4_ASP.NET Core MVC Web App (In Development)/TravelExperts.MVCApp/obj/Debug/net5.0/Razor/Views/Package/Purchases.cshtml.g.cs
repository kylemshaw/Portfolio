#pragma checksum "C:\Users\Kyle\source\repos\test\PROJ207_Term2-Final From GitHub\TravelExperts_WebApp_Fixed\TravelExperts.MVCApp\Views\Package\Purchases.cshtml" "{ff1816ec-aa5e-4d10-87f7-6f4963833460}" "34a78df40755208412800ceb7a356eda85469d87"
// <auto-generated/>
#pragma warning disable 1591
[assembly: global::Microsoft.AspNetCore.Razor.Hosting.RazorCompiledItemAttribute(typeof(AspNetCore.Views_Package_Purchases), @"mvc.1.0.view", @"/Views/Package/Purchases.cshtml")]
namespace AspNetCore
{
    #line hidden
    using System;
    using System.Collections.Generic;
    using System.Linq;
    using System.Threading.Tasks;
    using Microsoft.AspNetCore.Mvc;
    using Microsoft.AspNetCore.Mvc.Rendering;
    using Microsoft.AspNetCore.Mvc.ViewFeatures;
#nullable restore
#line 1 "C:\Users\Kyle\source\repos\test\PROJ207_Term2-Final From GitHub\TravelExperts_WebApp_Fixed\TravelExperts.MVCApp\Views\_ViewImports.cshtml"
using TravelExperts.MVCApp;

#line default
#line hidden
#nullable disable
#nullable restore
#line 2 "C:\Users\Kyle\source\repos\test\PROJ207_Term2-Final From GitHub\TravelExperts_WebApp_Fixed\TravelExperts.MVCApp\Views\_ViewImports.cshtml"
using TravelExperts.MVCApp.Models;

#line default
#line hidden
#nullable disable
    [global::Microsoft.AspNetCore.Razor.Hosting.RazorSourceChecksumAttribute(@"SHA1", @"34a78df40755208412800ceb7a356eda85469d87", @"/Views/Package/Purchases.cshtml")]
    [global::Microsoft.AspNetCore.Razor.Hosting.RazorSourceChecksumAttribute(@"SHA1", @"7cbdf4052203ab1a7381f6f91586294222beb6e6", @"/Views/_ViewImports.cshtml")]
    public class Views_Package_Purchases : global::Microsoft.AspNetCore.Mvc.Razor.RazorPage<IEnumerable<TravelExperts.MVCApp.Models.PackageViewModel>>
    {
        private static readonly global::Microsoft.AspNetCore.Razor.TagHelpers.TagHelperAttribute __tagHelperAttribute_0 = new global::Microsoft.AspNetCore.Razor.TagHelpers.TagHelperAttribute("class", new global::Microsoft.AspNetCore.Html.HtmlString("btn btn-primary text-light border float-right mr-3"), global::Microsoft.AspNetCore.Razor.TagHelpers.HtmlAttributeValueStyle.DoubleQuotes);
        private static readonly global::Microsoft.AspNetCore.Razor.TagHelpers.TagHelperAttribute __tagHelperAttribute_1 = new global::Microsoft.AspNetCore.Razor.TagHelpers.TagHelperAttribute("role", new global::Microsoft.AspNetCore.Html.HtmlString("button"), global::Microsoft.AspNetCore.Razor.TagHelpers.HtmlAttributeValueStyle.DoubleQuotes);
        private static readonly global::Microsoft.AspNetCore.Razor.TagHelpers.TagHelperAttribute __tagHelperAttribute_2 = new global::Microsoft.AspNetCore.Razor.TagHelpers.TagHelperAttribute("asp-action", "Checkout", global::Microsoft.AspNetCore.Razor.TagHelpers.HtmlAttributeValueStyle.DoubleQuotes);
        #line hidden
        #pragma warning disable 0649
        private global::Microsoft.AspNetCore.Razor.Runtime.TagHelpers.TagHelperExecutionContext __tagHelperExecutionContext;
        #pragma warning restore 0649
        private global::Microsoft.AspNetCore.Razor.Runtime.TagHelpers.TagHelperRunner __tagHelperRunner = new global::Microsoft.AspNetCore.Razor.Runtime.TagHelpers.TagHelperRunner();
        #pragma warning disable 0169
        private string __tagHelperStringValueBuffer;
        #pragma warning restore 0169
        private global::Microsoft.AspNetCore.Razor.Runtime.TagHelpers.TagHelperScopeManager __backed__tagHelperScopeManager = null;
        private global::Microsoft.AspNetCore.Razor.Runtime.TagHelpers.TagHelperScopeManager __tagHelperScopeManager
        {
            get
            {
                if (__backed__tagHelperScopeManager == null)
                {
                    __backed__tagHelperScopeManager = new global::Microsoft.AspNetCore.Razor.Runtime.TagHelpers.TagHelperScopeManager(StartTagHelperWritingScope, EndTagHelperWritingScope);
                }
                return __backed__tagHelperScopeManager;
            }
        }
        private global::Microsoft.AspNetCore.Mvc.TagHelpers.AnchorTagHelper __Microsoft_AspNetCore_Mvc_TagHelpers_AnchorTagHelper;
        #pragma warning disable 1998
        public async override global::System.Threading.Tasks.Task ExecuteAsync()
        {
            WriteLiteral("\n");
#nullable restore
#line 3 "C:\Users\Kyle\source\repos\test\PROJ207_Term2-Final From GitHub\TravelExperts_WebApp_Fixed\TravelExperts.MVCApp\Views\Package\Purchases.cshtml"
  
    ViewData["Title"] = "Purchases";

#line default
#line hidden
#nullable disable
            WriteLiteral("\n");
#nullable restore
#line 7 "C:\Users\Kyle\source\repos\test\PROJ207_Term2-Final From GitHub\TravelExperts_WebApp_Fixed\TravelExperts.MVCApp\Views\Package\Purchases.cshtml"
  
    decimal Sum = 0;
    string basePrice;
    foreach (var item in Model)
    {
        basePrice = item.PkgBasePrice.Substring(1);
        Sum += Convert.ToDecimal(basePrice);
    }


#line default
#line hidden
#nullable disable
            WriteLiteral("\n<h1>Ready to Depart!</h1>\n<h4>Please review your selections below.</h4>\n\n<table class=\"table\">\n    <thead>\n        <tr>\n            <th>\n                ");
#nullable restore
#line 25 "C:\Users\Kyle\source\repos\test\PROJ207_Term2-Final From GitHub\TravelExperts_WebApp_Fixed\TravelExperts.MVCApp\Views\Package\Purchases.cshtml"
           Write(Html.DisplayNameFor(model => model.PkgName));

#line default
#line hidden
#nullable disable
            WriteLiteral("\n            </th>\n            <th>\n                ");
#nullable restore
#line 28 "C:\Users\Kyle\source\repos\test\PROJ207_Term2-Final From GitHub\TravelExperts_WebApp_Fixed\TravelExperts.MVCApp\Views\Package\Purchases.cshtml"
           Write(Html.DisplayNameFor(model => model.PkgStartDate));

#line default
#line hidden
#nullable disable
            WriteLiteral("\n            </th>\n            <th>\n                ");
#nullable restore
#line 31 "C:\Users\Kyle\source\repos\test\PROJ207_Term2-Final From GitHub\TravelExperts_WebApp_Fixed\TravelExperts.MVCApp\Views\Package\Purchases.cshtml"
           Write(Html.DisplayNameFor(model => model.PkgEndDate));

#line default
#line hidden
#nullable disable
            WriteLiteral("\n            </th>\n            <th>\n                ");
#nullable restore
#line 34 "C:\Users\Kyle\source\repos\test\PROJ207_Term2-Final From GitHub\TravelExperts_WebApp_Fixed\TravelExperts.MVCApp\Views\Package\Purchases.cshtml"
           Write(Html.DisplayNameFor(model => model.PkgDesc));

#line default
#line hidden
#nullable disable
            WriteLiteral("\n            </th>\n            <th>\n                ");
#nullable restore
#line 37 "C:\Users\Kyle\source\repos\test\PROJ207_Term2-Final From GitHub\TravelExperts_WebApp_Fixed\TravelExperts.MVCApp\Views\Package\Purchases.cshtml"
           Write(Html.DisplayNameFor(model => model.PkgBasePrice));

#line default
#line hidden
#nullable disable
            WriteLiteral("\n            </th>\n            <th></th>\n        </tr>\n    </thead>\n    <tbody>\n");
#nullable restore
#line 43 "C:\Users\Kyle\source\repos\test\PROJ207_Term2-Final From GitHub\TravelExperts_WebApp_Fixed\TravelExperts.MVCApp\Views\Package\Purchases.cshtml"
         foreach (var item in Model)
        {

#line default
#line hidden
#nullable disable
            WriteLiteral("            <tr>\n                <td>\n                    ");
#nullable restore
#line 47 "C:\Users\Kyle\source\repos\test\PROJ207_Term2-Final From GitHub\TravelExperts_WebApp_Fixed\TravelExperts.MVCApp\Views\Package\Purchases.cshtml"
               Write(Html.DisplayFor(modelItem => item.PkgName));

#line default
#line hidden
#nullable disable
            WriteLiteral("\n                </td>\n                <td>\n                    ");
#nullable restore
#line 50 "C:\Users\Kyle\source\repos\test\PROJ207_Term2-Final From GitHub\TravelExperts_WebApp_Fixed\TravelExperts.MVCApp\Views\Package\Purchases.cshtml"
               Write(Html.DisplayFor(modelItem => item.PkgStartDate));

#line default
#line hidden
#nullable disable
            WriteLiteral("\n                </td>\n                <td>\n                    ");
#nullable restore
#line 53 "C:\Users\Kyle\source\repos\test\PROJ207_Term2-Final From GitHub\TravelExperts_WebApp_Fixed\TravelExperts.MVCApp\Views\Package\Purchases.cshtml"
               Write(Html.DisplayFor(modelItem => item.PkgEndDate));

#line default
#line hidden
#nullable disable
            WriteLiteral("\n                </td>\n                <td>\n                    ");
#nullable restore
#line 56 "C:\Users\Kyle\source\repos\test\PROJ207_Term2-Final From GitHub\TravelExperts_WebApp_Fixed\TravelExperts.MVCApp\Views\Package\Purchases.cshtml"
               Write(Html.DisplayFor(modelItem => item.PkgDesc));

#line default
#line hidden
#nullable disable
            WriteLiteral("\n                </td>\n                <td>\n                    ");
#nullable restore
#line 59 "C:\Users\Kyle\source\repos\test\PROJ207_Term2-Final From GitHub\TravelExperts_WebApp_Fixed\TravelExperts.MVCApp\Views\Package\Purchases.cshtml"
               Write(Html.DisplayFor(modelItem => item.PkgBasePrice));

#line default
#line hidden
#nullable disable
            WriteLiteral("\n                </td>\n            </tr>\n");
#nullable restore
#line 62 "C:\Users\Kyle\source\repos\test\PROJ207_Term2-Final From GitHub\TravelExperts_WebApp_Fixed\TravelExperts.MVCApp\Views\Package\Purchases.cshtml"
        }

#line default
#line hidden
#nullable disable
            WriteLiteral("\n        <tr>\n            <td></td>\n            <td></td>\n            <td></td>\n            <th class=\"text-right\">Total:</th>\n            <td>$");
#nullable restore
#line 69 "C:\Users\Kyle\source\repos\test\PROJ207_Term2-Final From GitHub\TravelExperts_WebApp_Fixed\TravelExperts.MVCApp\Views\Package\Purchases.cshtml"
            Write(Sum);

#line default
#line hidden
#nullable disable
            WriteLiteral("</td>\n        </tr>\n        <tr>\n            \n            <td class=\"border-0\" colspan=\"5\">\n                ");
            __tagHelperExecutionContext = __tagHelperScopeManager.Begin("a", global::Microsoft.AspNetCore.Razor.TagHelpers.TagMode.StartTagAndEndTag, "34a78df40755208412800ceb7a356eda85469d8710303", async() => {
                WriteLiteral("Purchase");
            }
            );
            __Microsoft_AspNetCore_Mvc_TagHelpers_AnchorTagHelper = CreateTagHelper<global::Microsoft.AspNetCore.Mvc.TagHelpers.AnchorTagHelper>();
            __tagHelperExecutionContext.Add(__Microsoft_AspNetCore_Mvc_TagHelpers_AnchorTagHelper);
            __tagHelperExecutionContext.AddHtmlAttribute(__tagHelperAttribute_0);
            __tagHelperExecutionContext.AddHtmlAttribute(__tagHelperAttribute_1);
            __Microsoft_AspNetCore_Mvc_TagHelpers_AnchorTagHelper.Action = (string)__tagHelperAttribute_2.Value;
            __tagHelperExecutionContext.AddTagHelperAttribute(__tagHelperAttribute_2);
            await __tagHelperRunner.RunAsync(__tagHelperExecutionContext);
            if (!__tagHelperExecutionContext.Output.IsContentModified)
            {
                await __tagHelperExecutionContext.SetOutputContentAsync();
            }
            Write(__tagHelperExecutionContext.Output);
            __tagHelperExecutionContext = __tagHelperScopeManager.End();
            WriteLiteral("\n            </td>\n            \n        </tr>\n    </tbody>\n</table>\n\n\n\n<div class=\"row mt-4\">\n    \n</div>\n\n");
        }
        #pragma warning restore 1998
        [global::Microsoft.AspNetCore.Mvc.Razor.Internal.RazorInjectAttribute]
        public global::Microsoft.AspNetCore.Mvc.ViewFeatures.IModelExpressionProvider ModelExpressionProvider { get; private set; }
        [global::Microsoft.AspNetCore.Mvc.Razor.Internal.RazorInjectAttribute]
        public global::Microsoft.AspNetCore.Mvc.IUrlHelper Url { get; private set; }
        [global::Microsoft.AspNetCore.Mvc.Razor.Internal.RazorInjectAttribute]
        public global::Microsoft.AspNetCore.Mvc.IViewComponentHelper Component { get; private set; }
        [global::Microsoft.AspNetCore.Mvc.Razor.Internal.RazorInjectAttribute]
        public global::Microsoft.AspNetCore.Mvc.Rendering.IJsonHelper Json { get; private set; }
        [global::Microsoft.AspNetCore.Mvc.Razor.Internal.RazorInjectAttribute]
        public global::Microsoft.AspNetCore.Mvc.Rendering.IHtmlHelper<IEnumerable<TravelExperts.MVCApp.Models.PackageViewModel>> Html { get; private set; }
    }
}
#pragma warning restore 1591