const input = document.getElementById('input_file');
const image = document.getElementById('img-preview');

input.addEventListener('change', (e) => {

    if (e.target.files.length) {
        const src = URL.createObjectURL(e.target.files[0]);
        image.src = src;
    }
});