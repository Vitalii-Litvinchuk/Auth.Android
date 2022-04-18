using AutoMapper;
using Microsoft.AspNetCore.Authorization;
using Microsoft.AspNetCore.Diagnostics;
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

        // Global handler
        //[Route("error")]
        //[AllowAnonymous]
        //[ApiExplorerSettings(IgnoreApi = true)]
        //public async Task<IActionResult> HadlerError()
        //{
        //    return BadRequest(new
        //    {
        //        errors = new
        //        {
        //            global = HttpContext.Features.Get<IExceptionHandlerFeature>().Error.Message
        //        }
        //    });
        //}
    }
}