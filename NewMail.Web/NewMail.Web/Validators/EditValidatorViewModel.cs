using FluentValidation;
using Microsoft.AspNetCore.Identity;
using NewMail.Web.Data.Entities.Identity;
using NewMail.Web.Models.Account;

namespace NewMail.Web.Validators
{
    public class EditValidatorViewModel : AbstractValidator<EditUser>

    {
        private readonly UserManager<AppUser> _userManager;
        public EditValidatorViewModel(UserManager<AppUser> userManager)
        {
            _userManager = userManager;
            RuleFor(x => x.Email)
               .NotEmpty().WithMessage("Поле пошта є обов'язковим!")
               .EmailAddress().WithMessage("Пошта є не коректною!");
               //.DependentRules(() =>
               //{
               //    RuleFor(x => x.Email).Must(BeUniqueEmail)

               //     .WithMessage("Дана пошта уже зареєстрована!");
               //});

            RuleFor(x => x.FirstName)
                .NotEmpty().WithMessage("Поле ім'я є обов'язковим!")
                .MinimumLength(2).WithMessage("Поле має мати міннімум 2 символів!");
        }
        //private bool BeUniqueEmail(string email)
        //{
        //    return _userManager.FindByEmailAsync(email).Result == null;
        //}
    }
}
