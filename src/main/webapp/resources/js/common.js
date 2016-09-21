function saveCookie(name, value) {
    var date = new Date();
    date.setTime(date.getTime()+(14*24*60*60*1000));
    document.cookie = name + '=' + value + "; path=/wiki/; expires=" + date.toGMTString();
}
function addBookmark(link) {
    var bookmarks = $.cookie("bookmarks");
    if(!bookmarks)
        bookmarks = link + ',,,';
    else
        bookmarks = bookmarks + link + ",,,";
    $.cookie('bookmarks', bookmarks, {expires: 14, path: '/' });
}
function removeBookmark(link) {
    var bookmarks = $.cookie("bookmarks");
    bookmarks = bookmarks.replace(link + ",,,", '');
    $.cookie('bookmarks', bookmarks, {expires: 14, path: '/' });
}
