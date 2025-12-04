document.querySelector(".btn-danger").addEventListener("click", () => {
  document.querySelector("form").action = "/book/remove";
  document.querySelector("form").submit();
});
