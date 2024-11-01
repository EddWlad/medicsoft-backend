using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Text.Json.Serialization;
using System.Threading.Tasks;

namespace MedicApp.DTO_s
{
    public class RoleDTO
    {
        public long id { get; set; }
        public string name { get; set; }
        public string description { get; set; }
        public int status { get; set; }

    }
}
