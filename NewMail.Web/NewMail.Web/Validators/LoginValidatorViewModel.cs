using FluentValidation;
using Microsoft.AspNetCore.Identity;
using NewMail.Web.Data.Entities.Identity;
using NewMail.Web.Models.Account;

namespace NewMail.Web.Validators
{
    public class LoginValidatorViewModel : AbstractValidator<LoginViewModel>
    {
        public LoginValidatorViewModel()
        {
            RuleFor(x => x.Email)
               .NotEmpty().WithMessage("Поле пошта є обов'язковим!")
               .EmailAddress().WithMessage("Пошта є не коректною!");

            RuleFor(x => x.Password)
                .NotEmpty().WithName("Password").WithMessage("Поле пароль є обов'язковим!");
        }
    }
}
