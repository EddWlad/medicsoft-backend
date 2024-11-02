using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace MedicApp.DTO_s
{
    public class PatientDTO
    {
        public long id { get; set; }
        public string name { get; set; }
        public string lastName { get; set; }
        public string department { get; set; }
        public int gender { get; set; }
        public float weight { get; set; }
        public float size { get; set; }
        public int year { get; set; }
        public string observation { get; set; }

        public int status { get; set; }

        public string StatusText => status != 1 ? "Inactivo" : "Activo";

        public string StatusColor => status != 1 ? "Red" : "Green";
    }
}
