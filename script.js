function unsafe_html_construction(html) {
  document.getElementById('insert-unsafe-html').innerHTML = html;
}

function logSubmit(event) {
  unsafe_html_construction(document.getElementById('html').value);
  event.preventDefault();
}

const form = document.getElementById('unsafe_html');
form.addEventListener('submit', logSubmit);

