package com.javarticles.java8.stream;

public class Camera {
	 // resolution is not optional - all cameras must have it
	  private Resolution resolution;
	 
	  public Resolution getResolution() {
	    return resolution;
	  }
	  /**
	   * Creating Optional objects:

Empty Optional: You can create an empty optional object use Optional.empty:
Optional<Phone> optPhone = Optional.empty();
Optional from a non-null value: To create an optional from a non-null value use Optional.of. Note that if the value is null a NullPointerException is thrown immediately:
Optional<Phone> optPhone = Optional.of(phone);
Optional from null: Use Optional.ofNullable to create an optional that may hold a null value. If the value is null, an empty optional is created.
Optional<Phone> optPhone = Optional.ofNullable(phone);
Optional<Camera> optCamera = Optional.ofNullable(camera);
Optional<Resolution> resolution = optCamera.map(Camera::getResolution);
Optional<Phone> optPhone = Optional.ofNullable(phone);
 
// calling map returns a two-level optional
Optional<Optional<Camera>> optOptCamera = optPhone.map(Phone::getCamera);
 
// but flatMap, returns a single-level optional
Optional<Camera> optCamera = optPhone.flatMap(Phone::getCamera);
public Resolution getPhoneCameraResolution(final Optional<Person> person) {
  return
    person.flatMap(Person::getPhone)  // returns Optional<Phone>
          .flatMap(Phone::getCamera) // returns Optional<Camera>
          .map(Camera::getResolution) // returns Optional<Resolution>
          .orElse(Resolution.UNKNOWN); // returns Resolution or UNKNOWN if not found
}
This code returns the resolution of the person's camera phone. If the person is null, doesn't have a phone, or the phone doesn't have a camera, an UNKNOWN resolution is returned.



	   */
}
