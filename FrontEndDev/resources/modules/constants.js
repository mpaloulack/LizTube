angular.module("liztube.constants",[

]).constant('constants',{
    SERVER_ERROR : 'Une erreur inattendue est survenue. Si le problème persiste veuillez contacter l\'équipe de Liztube.',
    FILE_TYPE_ERROR : 'Veuillez sélectionner une vidéo de type "mp4"',
    FILE_SIZE_ERROR: 'La taille de la vidéo ne doit pas dépasser 500 Mo',
    NO_FILE_SELECTED: 'Aucune vidéo sélectionnée',
    FILE_SIZE_ALLOWED: 524288000,
    UPLOAD_DONE: "Téléchargement de la vidéo terminé",
    DOWNLOAD_ON_AIR_FILE_NAME: "Téléchargement de la vidéo : ",
    NO_NOTIFICATIONS_FOUND: "Vous n'avez aucune notifications",
    NO_VIDEOS_FOUND: "Aucune vidéos trouvées"
});
