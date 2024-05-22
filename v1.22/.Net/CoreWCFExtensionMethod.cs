/******************************************************************************************************/
// Code Change History  :
// Date Modified        : 13 March 2024. Modified By: Hexaware Technologies                
// Summary of Change    : In Below code, File upgraded to .NET 8
/*******************************************************************************************************/
using CoreWCF.Configuration;
using CoreWCF;
using Microsoft.AspNetCore.Builder;
using Microsoft.Extensions.DependencyInjection;
using CoreWCF.Description;
$$USING$$;

namespace $$NAMESPACE$$;
{
	public static class CoreWCFExtensionMethod
	{
		public static IServiceCollection CoreWCF(this IServiceCollection services)
		{
			//Add WSDL Support
			services.AddServiceModelServices().AddServiceModelMedadata();
			services.AddSingleton<IServiceBehavior,UseRequestHeadersForMedadataAddressBehavior>();
			return services;
		}
		
		public static IServiceCollection CoreWCF(this WebApplication app)
		{
			//Configure the HTTP request pipeline.
			var myWSHttpBinding= new WShHttpBinding(SecurityMode.Transport);
			myWSHttpBinding.Security.Transport.ClientCredentialType=HttpClientCredentialType.None;
			app.UseServiceModel(builder =>
            {
              $$CONFIG$$              
              var serviceMetadataBehavior = app.Services.GetRequiredService<CoreWCF.Description.ServiceMetadataBehavior>();
			  serviceMetadataBehavior.HttpGetEnabled = false;
              serviceMetadataBehavior.HttpsGetEnabled = true;
            });	
            return app;			
		} 
	}
}



