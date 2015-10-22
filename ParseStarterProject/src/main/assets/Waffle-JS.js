document.write("you are in the script file")


    function getDensityDirectoryName() {
      if(!window.devicePixelRatio) {
          return 'mdpi';
      }

      if(window.devicePixelRatio > 1.5) {
          return 'xhdpi';
      } else if(window.devicePixelRatio > 1.0) {
          return 'hdpi';
      }

      return 'mdpi';
    }

    var baseUrl =
        'file:///android_asset/www/img-js-diff/ratiores/'+getDensityDirectoryName()+'/';
    document.write('<base href="'+baseUrl+'">');
	
	
	
	var WaffleMainPage = "WaffleMainPage.html";
	
	
	// this function can fire onclick handler for any DOM-Element
function fireClickEvent(element) {
    var evt = new window.MouseEvent('click', {
        view: window,
        bubbles: true,
        cancelable: true
    });

    element.dispatchEvent(evt);
}

// this function will setup a virtual anchor element
// and fire click handler to open new URL in the same room
// it works better than location.href=something or location.reload()
function openNewURLInTheSameWindow(WaffleMainPage) {
    var a = document.createElement('a');
    a.href = "WaffleMainPage.html";
    fireClickEvent(a);
}
	
	

	
	
	//<input type="submit" id="signIn" OnClick=WaffleMainPage>
