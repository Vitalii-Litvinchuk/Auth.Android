namespace NewMail.Web.Models.Account
{
    public class RegisterViewModel
    {
        /// <summary>
        /// Пошта користувача
        /// </summary>
        public string Email { get; set; }
        /// <summary>
        /// Ім'я користувача
        /// </summary>
        public string FirstName { get; set; }
        /// <summary>
        /// Прізвище користувача
        /// </summary>
        public string SecondName { get; set; }
        /// <summary>
        /// Фотографія (base64) користувача
        /// </summary>
        public string Photo { get; set; }
        /// <summary>
        /// Номер телефону користувача
        /// </summary>
        public string Phone { get; set; }
        /// <summary>
        /// Пароль користувача
        /// </summary>
        public string Password { get; set; }
        /// <summary>
        /// Повторний пароль користувача
        /// </summary>
        public string ConfirmPassword { get; set; }
    }

    public class LoginViewModel
    {
        /// <summary>
        /// Пошта користувача
        /// </summary>
        /// <example>qwerty@qwe.rty</example>
        public string Email { get; set; }
        /// <summary>
        /// Пароль користувача
        /// </summary>
        /// <example>qwerty</example>
        public string Password { get; set; }
    }

    public class UserItemViewModel
    {
        /// <summary>
        /// Ідентифікатор користувача
        /// </summary>
        public long Id { get; set; }
        /// <summary>
        /// Пошта користувача
        /// </summary>
        public string Email { get; set; }
        /// <summary>
        /// Ім'я користувача
        /// </summary>
        public string FirstName { get; set; }
        /// <summary>
        /// Прізвище користувача
        /// </summary>
        public string SecondName { get; set; }
        /// <summary>
        /// Фотографія (ім'я файлу) користувача
        /// </summary>
        public string Photo { get; set; }
        /// <summary>
        /// Номер телефону користувача
        /// </summary>
        public string Phone { get; set; }
    }
}
