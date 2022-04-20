using FluentValidation.AspNetCore;
using Microsoft.AspNetCore.Identity;
using Microsoft.EntityFrameworkCore;
using NewMail.Web.Data;
using NewMail.Web.Data.Entities.Identity;

namespace NewMail.Web.AppInit
{
    public static class InitDbContext
    {
        public static void UseAppDbConext(this WebApplicationBuilder builder)
        {
            builder.Services.AddDbContext<AppEFContext>(options =>
                options.UseNpgsql(builder.Configuration.GetConnectionString("DefaultConnection")));
            //options.UseMySQL(builder.Configuration.GetConnectionString("DefaultConnection")));
            // Add services to the container.

            builder.Services.AddIdentity<AppUser, AppRole>(options =>
            {
                options.Password.RequireDigit = false;
                options.Password.RequiredLength = 5;
                options.Password.RequireNonAlphanumeric = false;
                options.Password.RequireUppercase = false;
                options.Password.RequireLowercase = false;
            }).AddEntityFrameworkStores<AppEFContext>().AddDefaultTokenProviders();

            // Add services to the container.

            builder.Services.AddAutoMapper(AppDomain.CurrentDomain.GetAssemblies());

            builder.Services.AddFluentValidation(x => x.RegisterValidatorsFromAssemblyContaining<Program>());
        }
    }
}
