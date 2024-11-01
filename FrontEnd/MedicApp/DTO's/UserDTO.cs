using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace MedicApp.DTO_s
{
    public class UserDTO
    {
        public long id { get; set; }

        public long dateCreate { get; set; }
        public DateTime DateCreateAsDateTime => DateTimeOffset.FromUnixTimeMilliseconds(dateCreate).DateTime;
        public string identification { get; set; }
        public string name { get; set; }
        public string lastName { get; set; }
        public string username { get; set; }
        public string email { get; set; }
        public string password { get; set; }
        public RoleDTO role { get; set; } 
        public int status { get; set; }

        public string StatusText => status != 1 ? "Inactivo" : "Activo";

        public string StatusColor => status != 1 ? "Red" : "Green";

    }
}
