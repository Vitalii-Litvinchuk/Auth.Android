namespace NewMail.Web.Models.Account
{
    public class RegisterViewModel
    {
        /// <summary>
        /// Пошта користувача
        /// </summary>
        /// <example></example>
        public string Email { get; set; }
        /// <summary>
        /// Ім'я користувача
        /// </summary>
        /// <example></example>
        public string FirstName { get; set; }
        /// <summary>
        /// Прізвище користувача
        /// </summary>
        /// <example></example>
        public string SecondName { get; set; }
        /// <summary>
        /// Фотографія (base64) користувача
        /// </summary>
        /// <example></example>
        public string Photo { get; set; }
        /// <summary>
        /// Номер телефону користувача
        /// </summary>
        /// <example></example>
        public string Phone { get; set; }
        /// <summary>
        /// Пароль користувача
        /// </summary>
        /// <example></example>
        public string Password { get; set; }
        /// <summary>
        /// Повторний пароль користувача
        /// </summary>
        /// <example></example>
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

    public class EditUser {
        /// <summary>
        /// Ідентифікатор користувача
        /// </summary>
        /// <example>1</example>
        public string Id{ get; set; }
        /// <summary>
        /// Пошта користувача
        /// </summary>
        /// <example>qwerty@qwe.rty</example>
        public string Email { get; set; }
        /// <summary>
        /// Ім'я користувача
        /// </summary>
        /// <example>Qwerty</example>
        public string FirstName { get; set; }
        /// <summary>
        /// Прізвище користувача
        /// </summary>
        /// <example></example>
        public string SecondName { get; set; }
        /// <summary>
        /// Фотографія (base64) користувача
        /// </summary>
        /// <example></example>
        public string Photo { get; set; }
        /// <summary>
        /// Номер телефону користувача
        /// </summary>
        /// <example></example>
        public string Phone { get; set; }
    }


    public class UserItemViewModel
    {
        /// <summary>
        /// Ідентифікатор користувача
        /// </summary>
        /// <example></example>
        public long Id { get; set; }
        /// <summary>
        /// Пошта користувача
        /// </summary>
        /// <example></example>
        public string Email { get; set; }
        /// <summary>
        /// Ім'я користувача
        /// </summary>
        /// <example></example>
        public string FirstName { get; set; }
        /// <summary>
        /// Прізвище користувача
        /// </summary>
        /// <example></example>
        public string SecondName { get; set; }
        /// <summary>
        /// Фотографія (ім'я файлу) користувача
        /// </summary>
        /// <example></example>
        public string Photo { get; set; }
        /// <summary>
        /// Номер телефону користувача
        /// </summary>
        /// <example></example>
        public string Phone { get; set; }
    }
}
