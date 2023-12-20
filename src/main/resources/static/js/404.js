const url = "../404.json";
fetch(url)
  .then((response) => response.json())
  .then((animationData) => {
    const animationContainer = document.getElementById("animation");
    const animation = lottie.loadAnimation({
      container: animationContainer,
      renderer: "svg",
      loop: true,
      autoplay: true,
      animationData: animationData,
    });
  });
