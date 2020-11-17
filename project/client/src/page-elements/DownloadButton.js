import React from 'react';
import { PDFDownloadLink, Page, Text, View, Document, StyleSheet } from '@react-pdf/renderer';
import ReactDOM from 'react-dom';
import { PDFViewer } from '@react-pdf/renderer';




export default function PdfExport() {
    // return (
    //     <PDFViewer>
    //         <MyDocument />
    //     </PDFViewer>
    // )

    return (
        <div>
            <PDFDownloadLink document={<MyDocument />} fileName="somename.pdf">
                {({ blob, url, loading, error }) => (loading ? 'Loading document...' : 'Download now!')}
            </PDFDownloadLink>
        </div>
    )
}







