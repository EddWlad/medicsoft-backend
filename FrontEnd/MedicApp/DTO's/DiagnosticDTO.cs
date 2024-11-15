using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace MedicApp.DTO_s
{
    public class DiagnosticDTO
    {
        public long id { get; set; }

        public long diagnosticDate { get; set; }
        public DateTime DateCreateAsDateTime => DateTimeOffset.FromUnixTimeMilliseconds(diagnosticDate).DateTime;
        public string symptoms { get; set; }
        public string diagnostic { get; set; }
        public string observation { get; set; }
        public PatientDTO patient { get; set; }

        public bool isNew { get; set; } = true;
        public int status { get; set; }

        public string StatusText => status != 1 ? "Inactivo" : "Activo";

        public string StatusColor => status != 1 ? "Red" : "Green";
    }
}
