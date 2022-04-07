using Microsoft.AspNetCore.Mvc;
using NewMail.Web.Models;
using NewMail.Web.Root;
using System.Drawing;

namespace NewMail.Web.Controllers
{
    [ApiController]
    [Route("image/")]
    public class ImageController : ControllerBase
    {
        private readonly IWebHostEnvironment _webHostEnvironment;
        private readonly ILogger<ImageController> _logger;

        public ImageController(ILogger<ImageController> logger, IWebHostEnvironment webHostEnvironment)
        {
            _webHostEnvironment = webHostEnvironment;
            _logger = logger;
        }

        [HttpPost("upload")]
        public async Task<IActionResult> UploadImage([FromBody] ImageUpload image)
        {
            string uniqueFileName = "";
            if (image != null)
            {
                string uploadsFolder = Path.Combine(DirectoryService.rootDirectory, "uploads");
                uniqueFileName = Guid.NewGuid().ToString();
                string filePath = Path.Combine(uploadsFolder, uniqueFileName + ".jpg");
                var bytes = Convert.FromBase64String(image.image);
                System.IO.File.WriteAllBytes(filePath, bytes);

                string file270x360 = Path.Combine(uploadsFolder, uniqueFileName + "(270x360.jpg");
                string file450x600 = Path.Combine(uploadsFolder, uniqueFileName + "(450x600.jpg");
                string file690x920 = Path.Combine(uploadsFolder, uniqueFileName + "(690x920.jpg");
                string file810x1080 = Path.Combine(uploadsFolder, uniqueFileName + "(810x1080.jpg");

                Bitmap tmpmap = new Bitmap(filePath);
                Bitmap bmp = new Bitmap(tmpmap, 270, 360);
                bmp.Save(file270x360);
                bmp.Dispose();

                tmpmap = new Bitmap(filePath);
                bmp = new Bitmap(tmpmap, 450, 600);
                bmp.Save(file450x600);
                bmp.Dispose();

                tmpmap = new Bitmap(filePath);
                bmp = new Bitmap(tmpmap, 690, 920);
                bmp.Save(file690x920);
                bmp.Dispose();

                tmpmap = new Bitmap(filePath);
                bmp = new Bitmap(tmpmap, 810, 1080);
                bmp.Save(file810x1080);
                bmp.Dispose();
            }
            else return BadRequest();
            var file = new { filename = uniqueFileName + ".jpg" };
            return Ok(file);
        }

    }
}