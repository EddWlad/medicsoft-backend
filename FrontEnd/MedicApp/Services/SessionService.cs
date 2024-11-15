using MedicApp.DTO_s;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace MedicApp.Services
{
    public class SessionService
    {
        private static SessionService _instance;
        public static SessionService Instance => _instance ??= new SessionService();

        private UserDTO _user;

        public void SetUser(UserDTO user)
        {
            _user = user;
        }

        public UserDTO GetUser()
        {
            return _user;
        }

        public void ClearUser()
        {
            _user = null;
        }
    }
}
