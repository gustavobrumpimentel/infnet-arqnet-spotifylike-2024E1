﻿using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace SpotifyLike.Domain.Streaming
{
    public class Banda
    {
        public Guid Id { get; set; }
        public String Nome { get; set; }
        public String Descricao { get; set; }
        public List<Album> Albums { get; set; }
    }
}
