var huellero = {
  capturar: function (success, error) {
    var options = {};
    cordova.exec(success, error, 'Huellero', 'capturar', [options]);
  }
}

cordova.addConstructor(function () {
  if (!window.plugins) {
    window.plugins = {};
  }

  window.plugins.huellero = huellero;
  return window.plugins.huellero;
});