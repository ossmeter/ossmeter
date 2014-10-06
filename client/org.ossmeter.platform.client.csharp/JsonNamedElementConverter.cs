using System;
using System.Net;
using System.IO;
using Newtonsoft.Json;
using System.Collections.Generic;
using Newtonsoft.Json.Linq;

namespace ossmeterclient.converters
{

	class JsonNamedElementConverter : JsonCreationConverter<NamedElement>
	{
		protected override NamedElement Create(Type objectType, JObject jsonObject)
		{
			var typeName = jsonObject ["_type"].ToString ();
			switch (typeName) {
				case "org.ossmeter.repository.model.ProjectRepository":
					return new ProjectRepository ();
				case "org.ossmeter.repository.model.Project":
					return new Project ();
				case "org.ossmeter.repository.model.MetricProvider":
					return new MetricProvider ();
				case "org.ossmeter.repository.model.VcsRepository":
					return new VcsRepository ();
				case "org.ossmeter.repository.model.Person":
					return new Person ();
				case "org.ossmeter.repository.model.Role":
					return new Role ();
				case "org.ossmeter.repository.model.License":
					return new License ();
				default:
					return null;
			}
		}
	}
}
