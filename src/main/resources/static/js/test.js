 // MESS

var App = new Vue({
  el: "#app",
  data: {
    password: null,
    password_length: 0,
    contains_eight_characters: false,
    contains_number: false,
    valid: false,
    contains_uppercase: false,
    contains_special_character: false,
    valid_password: false, },


  methods: {
    checkPassword: function checkPassword() {
      this.password_length = this.password.length;
      var format = /[ !@#$%^&*()_+\-=\[\]{};':"\\|,.<>\/?]/;

      if (this.password_length > 4) {
        this.contains_eight_characters = true;
      } else {
        this.contains_eight_characters = false;
      }

      this.contains_number = /\d/.test(this.password);
      this.contains_uppercase = /[A-Z]/.test(this.password);
      this.contains_special_character = format.test(this.password);
      
      if(document.getElementById("confirm_password").value === document.getElementById("password").value)
    	  {
    	  	this.valid = true;
    	  }
      else
    	  {
  	  	this.valid = false;
    	  }
      
      if (this.contains_eight_characters === true &&
      this.contains_special_character === true &&
      this.contains_uppercase === true &&
      this.contains_number === true &&
      this.valid === true) {
        this.valid_password = true;
        document.getElementById('ss').style.visibility = 'visible';

      } else {
        this.valid_password = false;
        document.getElementById('ss').style.visibility = 'hidden';
      }
      

      if(document.getElementById("confirm_password").value === document.getElementById("password").value)
	  {
	  	this.valid = true;
	  }
  else
	  {
	  	this.valid = false;
	  }
      
    } } });
