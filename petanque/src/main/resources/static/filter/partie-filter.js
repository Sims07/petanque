angular.module('PetanqueFilters').filter('partieFilter', function() {
	return function(items, partieFilterModel) {
		var filtered = [];
		if (!partieFilterModel.partieTermine && (partieFilterModel.numero === undefined || partieFilterModel.numero == '')) {
			return items;
		} else if (partieFilterModel.partieTermine && (partieFilterModel.numero === undefined || partieFilterModel.numero == '')) {
			angular.forEach(items, function(item) {
				if (!item.termine) {
					filtered.push(item);
				}
			});
		} else {
			angular.forEach(items, function(item) {
				if (partieFilterModel.partieTermine) {
					if (!item.termine && (item.equipe1.numero == partieFilterModel.numero || item.equipe2.numero == partieFilterModel.numero)) {
						filtered.push(item);
					}
				} else if (item.equipe1.numero == partieFilterModel.numero || item.equipe2.numero == partieFilterModel.numero) {
					filtered.push(item);
				}
			});
		}
		return filtered;
	};
});