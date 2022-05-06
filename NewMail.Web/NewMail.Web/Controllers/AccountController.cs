using AutoMapper;
using Microsoft.AspNetCore.Authorization;
using Microsoft.AspNetCore.Identity;
using Microsoft.AspNetCore.Mvc;
using NewMail.Web.Data;
using NewMail.Web.Data.Entities.Identity;
using NewMail.Web.Helpers;
using NewMail.Web.Models.Account;
using NewMail.Web.Services;
using System.Drawing.Imaging;

namespace NewMail.Web.Controllers
{
    [Authorize]
    [ApiController]
    [Route("api/account")]
    public class AccountController : ControllerBase
    {
        private readonly IMapper _mapper;
        private readonly IJwtTokenService _jwtTokenService;
        private readonly UserManager<AppUser> _userManager;

        //private readonly ILogger<AccountController> _logger;
        private readonly AppEFContext _context;
        public AccountController(UserManager<AppUser> userManager,
            IJwtTokenService jwtTokenService, IMapper mapper, AppEFContext context)
        {
            _userManager = userManager;
            _mapper = mapper;
            _jwtTokenService = jwtTokenService;
            _context = context;
        }

        /// <summary>
        /// Реєстрація [Unauthorize]
        /// </summary>
        /// <param name="model">Пошта, ім'я, прізвище, фотографія (base64), номер телефону, пароль, повторний пароль</param>
        /// <returns>Jwt token sha256</returns>
        // <response code="500">Помилка сервера</response>
        // <remarks>Awesomeness!</remarks>
        // <response code="200">Register user</response>
        // <response code="400">Register has missing/invalid values</response>
        [HttpPost]
        [AllowAnonymous]
        [Route("register")]
        public async Task<IActionResult> Register([FromBody] RegisterViewModel model)
        {
            var img = ImageWorker.FromBase64StringToImage(model.Photo);
            string randomFilename = Path.GetRandomFileName() + ".jpeg";
            var dir = Path.Combine(Directory.GetCurrentDirectory(), "uploads", randomFilename);
            img.Save(dir, ImageFormat.Jpeg);
            var user = _mapper.Map<AppUser>(model);
            user.Photo = randomFilename;
            var result = await _userManager.CreateAsync(user, model.Password);

            if (!result.Succeeded)
                return BadRequest(new { errors = result.Errors });


            return Ok(new { token = _jwtTokenService.CreateToken(user) });
        }

        /// <summary>
        /// Вхід [Unauthorize]
        /// </summary>
        /// <param name="model">Пошта, пароль</param>
        /// <returns>Jwt token sha256</returns>
        [HttpPost]
        [AllowAnonymous]
        [Route("login")]
        public async Task<IActionResult> Login([FromBody] LoginViewModel model)
        {
            AppUser user = await _userManager.FindByEmailAsync(model.Email);
            if (user != null)
            {
                if (await _userManager.CheckPasswordAsync(user, model.Password))
                {
                    return Ok(new { token = _jwtTokenService.CreateToken(user) });
                }
            }
            return BadRequest(new { errors = new { global = "Авторизація неуспішна" } });
        }

        /// <summary>
        /// Змінити дані користувача [Authorize]
        /// </summary>
        /// <param name="model">Параметри для зміни</param>
        /// <returns></returns>
        /// <exception cref="InvalidDataException"></exception>
        [HttpPut]
        [Route("edit")]
        public async Task<IActionResult> Edit([FromBody] EditUser model)
        {
            if (model != null)
            {
                //var user = await _context.Users.FindAsync(long.Parse(model.Id));
                var user = await _userManager.FindByIdAsync(model.Id);
                if (user != null)
                {
                    if (!String.IsNullOrEmpty(model.FirstName) && !user.FirstName.Equals(model.FirstName))
                        user.FirstName = model.FirstName;

                    if (!String.IsNullOrEmpty(model.SecondName) && !user.SecondName.Equals(model.SecondName))
                        user.SecondName = model.SecondName;

                    if (!String.IsNullOrEmpty(model.Photo) && !(model.Photo.Split("/").Length > 1))
                    {
                        var img = ImageWorker.FromBase64StringToImage(model.Photo);
                        string randomFilename = Path.GetRandomFileName() + ".jpeg";
                        var dir = Path.Combine(Directory.GetCurrentDirectory(), "uploads", randomFilename);
                        img.Save(dir, ImageFormat.Jpeg);

                        //var dirDelete = Path.Combine(Directory.GetCurrentDirectory(), "uploads", user.Photo);
                        //System.IO.File.Delete(dirDelete);

                        user.Photo = randomFilename;
                    }

                    if (!String.IsNullOrEmpty(model.Phone) && !user.PhoneNumber.Equals(model.Phone))
                        user.PhoneNumber = model.Phone;

                    await _userManager.UpdateAsync(user);
                    //_context.Users.Update(user);
                    //await _context.SaveChangesAsync();
                    return Ok(new { message = "Успішно змінено" });
                }
            }
            throw new InvalidDataException();
        }

        /// <summary>
        /// Вивід користувачів [Authorize]
        /// </summary>
        /// <param name="model"></param>
        /// <returns>Всі користувачі</returns>
        [HttpGet]
        [Route("users")]
        public async Task<IActionResult> Users()
        {
            var list = _context.Users.Select(x => _mapper.Map<UserItemViewModel>(x)).ToList();

            return Ok(new { users = list });
        }

        /// <summary>
        /// Дані користувача [Authorize]
        /// </summary>
        /// <returns>UserItemViewModel</returns>
        [HttpGet]
        [Route("user/{id}")]
        public async Task<IActionResult> User(string id)
        {
            var user = _mapper.Map<EditUser>( await _context.Users.FindAsync(long.Parse(id)));
            return Ok(new { user = user });
        }

    }
}