//LO que expone el JS de cordova a la aplicacion que lo va a consumir
var huellero = {
  capturar: function (success, error) {
    var options = {};
    cordova.exec(success, error, 'Huellero', 'capturar', [options]);
  },
  conectar: function (success, error) {
    var options = {};
    cordova.exec(success, error, 'Huellero', 'conectar', [options]);
  }
}

//NO TOCAR, SOLO INSTALACION
cordova.addConstructor(function () {
  if (!window.plugins) {
    window.plugins = {};
  }

  window.plugins.huellero = huellero;
  return window.plugins.huellero;
});