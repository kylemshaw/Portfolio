#pragma checksum "C:\Users\Kyle\source\repos\test\PROJ207_Term2-Final From GitHub\TravelExperts_WebApp_Fixed\TravelExperts.MVCApp\Views\Package\Index.cshtml" "{ff1816ec-aa5e-4d10-87f7-6f4963833460}" "d4776a0ef25786ee15e73f9c804ba0b08593b778"
// <auto-generated/>
#pragma warning disable 1591
[assembly: global::Microsoft.AspNetCore.Razor.Hosting.RazorCompiledItemAttribute(typeof(AspNetCore.Views_Package_Index), @"mvc.1.0.view", @"/Views/Package/Index.cshtml")]
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
    [global::Microsoft.AspNetCore.Razor.Hosting.RazorSourceChecksumAttribute(@"SHA1", @"d4776a0ef25786ee15e73f9c804ba0b08593b778", @"/Views/Package/Index.cshtml")]
    [global::Microsoft.AspNetCore.Razor.Hosting.RazorSourceChecksumAttribute(@"SHA1", @"7cbdf4052203ab1a7381f6f91586294222beb6e6", @"/Views/_ViewImports.cshtml")]
    public class Views_Package_Index : global::Microsoft.AspNetCore.Mvc.Razor.RazorPage<IEnumerable<TravelExperts.MVCApp.Models.PackageViewModel>>
    {
        private static readonly global::Microsoft.AspNetCore.Razor.TagHelpers.TagHelperAttribute __tagHelperAttribute_0 = new global::Microsoft.AspNetCore.Razor.TagHelpers.TagHelperAttribute("class", new global::Microsoft.AspNetCore.Html.HtmlString("card-img pt-1"), global::Microsoft.AspNetCore.Razor.TagHelpers.HtmlAttributeValueStyle.DoubleQuotes);
        private static readonly global::Microsoft.AspNetCore.Razor.TagHelpers.TagHelperAttribute __tagHelperAttribute_1 = new global::Microsoft.AspNetCore.Razor.TagHelpers.TagHelperAttribute("alt", new global::Microsoft.AspNetCore.Html.HtmlString("click to buy"), global::Microsoft.AspNetCore.Razor.TagHelpers.HtmlAttributeValueStyle.DoubleQuotes);
        private static readonly global::Microsoft.AspNetCore.Razor.TagHelpers.TagHelperAttribute __tagHelperAttribute_2 = new global::Microsoft.AspNetCore.Razor.TagHelpers.TagHelperAttribute("class", new global::Microsoft.AspNetCore.Html.HtmlString("card h-100 btn shadow-sm"), global::Microsoft.AspNetCore.Razor.TagHelpers.HtmlAttributeValueStyle.DoubleQuotes);
        private static readonly global::Microsoft.AspNetCore.Razor.TagHelpers.TagHelperAttribute __tagHelperAttribute_3 = new global::Microsoft.AspNetCore.Razor.TagHelpers.TagHelperAttribute("asp-controller", "Package", global::Microsoft.AspNetCore.Razor.TagHelpers.HtmlAttributeValueStyle.DoubleQuotes);
        private static readonly global::Microsoft.AspNetCore.Razor.TagHelpers.TagHelperAttribute __tagHelperAttribute_4 = new global::Microsoft.AspNetCore.Razor.TagHelpers.TagHelperAttribute("asp-action", "Selected", global::Microsoft.AspNetCore.Razor.TagHelpers.HtmlAttributeValueStyle.DoubleQuotes);
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
        private global::Microsoft.AspNetCore.Mvc.Razor.TagHelpers.UrlResolutionTagHelper __Microsoft_AspNetCore_Mvc_Razor_TagHelpers_UrlResolutionTagHelper;
        #pragma warning disable 1998
        public async override global::System.Threading.Tasks.Task ExecuteAsync()
        {
            WriteLiteral("\n");
#nullable restore
#line 3 "C:\Users\Kyle\source\repos\test\PROJ207_Term2-Final From GitHub\TravelExperts_WebApp_Fixed\TravelExperts.MVCApp\Views\Package\Index.cshtml"
  
    ViewData["Title"] = "Index";

#line default
#line hidden
#nullable disable
            WriteLiteral("\n<h1>Select a vacation package:</h1>\n<div class=\"container\">\n    <div class=\"mt-5\">\n        <div class=\"row row-cols-1 row-cols-md-2 row-cols-lg-3 row-cols-xl-4 g-4 mt-5 \">\n");
#nullable restore
#line 11 "C:\Users\Kyle\source\repos\test\PROJ207_Term2-Final From GitHub\TravelExperts_WebApp_Fixed\TravelExperts.MVCApp\Views\Package\Index.cshtml"
             foreach (var item in Model)
            {
                

#line default
#line hidden
#nullable disable
            WriteLiteral("                <div class=\"col-md-4 my-3 packageCard\">\n                    ");
            __tagHelperExecutionContext = __tagHelperScopeManager.Begin("a", global::Microsoft.AspNetCore.Razor.TagHelpers.TagMode.StartTagAndEndTag, "d4776a0ef25786ee15e73f9c804ba0b08593b7786024", async() => {
                WriteLiteral("\n                        ");
                __tagHelperExecutionContext = __tagHelperScopeManager.Begin("img", global::Microsoft.AspNetCore.Razor.TagHelpers.TagMode.StartTagOnly, "d4776a0ef25786ee15e73f9c804ba0b08593b7786301", async() => {
                }
                );
                __Microsoft_AspNetCore_Mvc_Razor_TagHelpers_UrlResolutionTagHelper = CreateTagHelper<global::Microsoft.AspNetCore.Mvc.Razor.TagHelpers.UrlResolutionTagHelper>();
                __tagHelperExecutionContext.Add(__Microsoft_AspNetCore_Mvc_Razor_TagHelpers_UrlResolutionTagHelper);
                BeginAddHtmlAttributeValues(__tagHelperExecutionContext, "src", 2, global::Microsoft.AspNetCore.Razor.TagHelpers.HtmlAttributeValueStyle.DoubleQuotes);
                AddHtmlAttributeValue("", 574, "~/images/", 574, 9, true);
#nullable restore
#line 16 "C:\Users\Kyle\source\repos\test\PROJ207_Term2-Final From GitHub\TravelExperts_WebApp_Fixed\TravelExperts.MVCApp\Views\Package\Index.cshtml"
AddHtmlAttributeValue("", 583, Html.DisplayFor(modelItem => item.PackagePicture), 583, 50, false);

#line default
#line hidden
#nullable disable
                EndAddHtmlAttributeValues(__tagHelperExecutionContext);
                __tagHelperExecutionContext.AddHtmlAttribute(__tagHelperAttribute_0);
                __tagHelperExecutionContext.AddHtmlAttribute(__tagHelperAttribute_1);
                await __tagHelperRunner.RunAsync(__tagHelperExecutionContext);
                if (!__tagHelperExecutionContext.Output.IsContentModified)
                {
                    await __tagHelperExecutionContext.SetOutputContentAsync();
                }
                Write(__tagHelperExecutionContext.Output);
                __tagHelperExecutionContext = __tagHelperScopeManager.End();
                WriteLiteral("\n                        <div class=\"card-body\">\n                            <h5 class=\"card-title\">");
#nullable restore
#line 18 "C:\Users\Kyle\source\repos\test\PROJ207_Term2-Final From GitHub\TravelExperts_WebApp_Fixed\TravelExperts.MVCApp\Views\Package\Index.cshtml"
                                              Write(Html.DisplayFor(modelItem => item.PkgName));

#line default
#line hidden
#nullable disable
                WriteLiteral("</h5>\n                            <p>\n                                Price: <span id=\"price\">");
#nullable restore
#line 20 "C:\Users\Kyle\source\repos\test\PROJ207_Term2-Final From GitHub\TravelExperts_WebApp_Fixed\TravelExperts.MVCApp\Views\Package\Index.cshtml"
                                                   Write(Html.DisplayFor(modelItem => item.PkgBasePrice));

#line default
#line hidden
#nullable disable
                WriteLiteral("</span><br />\n                                Departs: <span id=\"startDate\"> ");
#nullable restore
#line 21 "C:\Users\Kyle\source\repos\test\PROJ207_Term2-Final From GitHub\TravelExperts_WebApp_Fixed\TravelExperts.MVCApp\Views\Package\Index.cshtml"
                                                          Write(Html.DisplayFor(modelItem => item.PkgStartDate));

#line default
#line hidden
#nullable disable
                WriteLiteral(" </span> <br />\n                                Returns: <span id=\"endDate\">");
#nullable restore
#line 22 "C:\Users\Kyle\source\repos\test\PROJ207_Term2-Final From GitHub\TravelExperts_WebApp_Fixed\TravelExperts.MVCApp\Views\Package\Index.cshtml"
                                                       Write(Html.DisplayFor(modelItem => item.PkgEndDate));

#line default
#line hidden
#nullable disable
                WriteLiteral("</span>\n                            </p>\n                            <p class=\"card-text\">");
#nullable restore
#line 24 "C:\Users\Kyle\source\repos\test\PROJ207_Term2-Final From GitHub\TravelExperts_WebApp_Fixed\TravelExperts.MVCApp\Views\Package\Index.cshtml"
                                            Write(Html.DisplayFor(modelItem => item.PkgDesc));

#line default
#line hidden
#nullable disable
                WriteLiteral("</p>\n                        </div>\n                    ");
            }
            );
            __Microsoft_AspNetCore_Mvc_TagHelpers_AnchorTagHelper = CreateTagHelper<global::Microsoft.AspNetCore.Mvc.TagHelpers.AnchorTagHelper>();
            __tagHelperExecutionContext.Add(__Microsoft_AspNetCore_Mvc_TagHelpers_AnchorTagHelper);
            __tagHelperExecutionContext.AddHtmlAttribute(__tagHelperAttribute_2);
            __Microsoft_AspNetCore_Mvc_TagHelpers_AnchorTagHelper.Controller = (string)__tagHelperAttribute_3.Value;
            __tagHelperExecutionContext.AddTagHelperAttribute(__tagHelperAttribute_3);
            __Microsoft_AspNetCore_Mvc_TagHelpers_AnchorTagHelper.Action = (string)__tagHelperAttribute_4.Value;
            __tagHelperExecutionContext.AddTagHelperAttribute(__tagHelperAttribute_4);
            if (__Microsoft_AspNetCore_Mvc_TagHelpers_AnchorTagHelper.RouteValues == null)
            {
                throw new InvalidOperationException(InvalidTagHelperIndexerAssignment("asp-route-id", "Microsoft.AspNetCore.Mvc.TagHelpers.AnchorTagHelper", "RouteValues"));
            }
            BeginWriteTagHelperAttribute();
#nullable restore
#line 15 "C:\Users\Kyle\source\repos\test\PROJ207_Term2-Final From GitHub\TravelExperts_WebApp_Fixed\TravelExperts.MVCApp\Views\Package\Index.cshtml"
                                                                                                         WriteLiteral(item.PackageId);

#line default
#line hidden
#nullable disable
            __tagHelperStringValueBuffer = EndWriteTagHelperAttribute();
            __Microsoft_AspNetCore_Mvc_TagHelpers_AnchorTagHelper.RouteValues["id"] = __tagHelperStringValueBuffer;
            __tagHelperExecutionContext.AddTagHelperAttribute("asp-route-id", __Microsoft_AspNetCore_Mvc_TagHelpers_AnchorTagHelper.RouteValues["id"], global::Microsoft.AspNetCore.Razor.TagHelpers.HtmlAttributeValueStyle.DoubleQuotes);
            await __tagHelperRunner.RunAsync(__tagHelperExecutionContext);
            if (!__tagHelperExecutionContext.Output.IsContentModified)
            {
                await __tagHelperExecutionContext.SetOutputContentAsync();
            }
            Write(__tagHelperExecutionContext.Output);
            __tagHelperExecutionContext = __tagHelperScopeManager.End();
            WriteLiteral("\n                </div>\n");
#nullable restore
#line 28 "C:\Users\Kyle\source\repos\test\PROJ207_Term2-Final From GitHub\TravelExperts_WebApp_Fixed\TravelExperts.MVCApp\Views\Package\Index.cshtml"
            }

#line default
#line hidden
#nullable disable
            WriteLiteral("        </div>\n    </div>\n</div>\n\n");
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
