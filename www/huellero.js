var huellero = {
  capturar: function (arg0, success, error) {
    exec(success, error, 'huellero', 'capturar', [arg0]);
  }
}

cordova.addConstructor(function () {
  if (!window.plugins) {
    window.plugins = {};
  }

  window.plugins.huellero = huellero;
  return window.plugins.huellero;
});