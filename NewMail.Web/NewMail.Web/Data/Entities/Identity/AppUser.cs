﻿using Microsoft.AspNetCore.Identity;
using System.ComponentModel.DataAnnotations;

namespace NewMail.Web.Data.Entities.Identity
{
    public class AppUser : IdentityUser<long>
    {
        [StringLength(100)]
        public string FirstName { get; set; }
        [StringLength(100)]
        public string SecondName { get; set; }
        [StringLength(100)]
        public string Photo { get; set; }
        public virtual ICollection<AppUserRole> UserRoles { get; set; }
    }
}
