var gulp = require('gulp');
var browserSync = require('browser-sync').create();
var reload = browserSync.reload;
// Static server
gulp.task('browser-sync', function() {
	browserSync.init({
		server: {
			baseDir: "./src/main/resources/static"
		}
	});
	gulp.watch("./src/main/resources/static/partials/*").on('change', reload);
	gulp.watch("./src/main/resources/static/directives/*").on('change', reload);
	gulp.watch("./src/main/resources/static/*").on('change', reload);
	gulp.watch("./src/main/resources/static/petanque/*").on('change', reload);
});



gulp.task('default', ['browser-sync']);